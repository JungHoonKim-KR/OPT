import express from "express";
import cors from "cors";
import path from "path";
import { fileURLToPath } from "url";
import { Jimp } from "jimp";
import USB from "@node-escpos/usb-adapter";
import fs from "fs";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const app = express();
const PORT = 3001; // 프린터 서버는 3001 포트 사용

// 프린터 설정
const PRINTER_WIDTH = 576;
const CMD_LEFT = Buffer.from([0x1b, 0x61, 0x00]);

// CORS 설정 - 프론트엔드에서 접근 가능하도록
app.use(
  cors({
    origin: "*", // 또는 특정 도메인만: 'http://localhost:5173'
    methods: ["POST", "GET"],
    credentials: true,
  })
);

app.use(express.json());

// 프린터 상태 확인 API
app.get("/api/printer/status", (req, res) => {
  res.json({
    success: true,
    message: "프린터 서버 실행 중",
    port: PORT,
  });
});

// 프린트 API (프린터 출력만 담당)
app.post("/api/print", async (req, res) => {
  let device, printer;

  try {
    const { typeCode } = req.body;

    if (!typeCode) {
      return res.status(400).json({
        success: false,
        message: "typeCode가 필요합니다.",
      });
    }

    console.log(`📄 프린트 시작: ${typeCode}`);

    // 이미지 경로 (프로젝트 폴더 내 print-images 폴더)
    const imgPath = path.join(__dirname, "print-images", `${typeCode}.png`);

    // 이미지 파일 존재 확인
    if (!fs.existsSync(imgPath)) {
      console.error(`❌ 이미지 파일 없음: ${imgPath}`);
      return res.status(404).json({
        success: false,
        message: `${typeCode} 이미지를 찾을 수 없습니다.`,
      });
    }

    // USB 프린터 연결
    device = new USB();
    await device.open();
    printer = device;
    console.log("✅ 프린터 연결 성공");

    // 이미지 처리
    let img = await Jimp.read(imgPath);
    img.resize(PRINTER_WIDTH, Jimp.AUTO);
    img = img.grayscale().contrast(0.5);

    const width = img.bitmap.width;
    const height = img.bitmap.height;

    // 비트맵 데이터 변환
    const data = [];
    for (let y = 0; y < height; y++) {
      for (let x = 0; x < width; x += 8) {
        let byte = 0;
        for (let bit = 0; bit < 8; bit++) {
          if (x + bit < width) {
            const idx = (y * width + (x + bit)) * 4;
            const pixel = img.bitmap.data[idx];
            if (pixel < 128) byte |= 1 << (7 - bit);
          }
        }
        data.push(byte);
      }
    }

    // 프린터 명령 전송
    await printer.write(CMD_LEFT);

    const bytesPerLine = PRINTER_WIDTH / 8;
    const rasterHeader = [
      0x1d,
      0x76,
      0x30,
      0x00,
      bytesPerLine % 256,
      Math.floor(bytesPerLine / 256),
      height % 256,
      Math.floor(height / 256),
    ];

    await printer.write(Buffer.from([...rasterHeader, ...data]));
    await printer.write(CMD_LEFT);
    await printer.write(Buffer.from([0x1b, 0x64, 0x03])); // Feed
    await printer.write(Buffer.from([0x1d, 0x56, 0x41, 0x00])); // Cut

    await device.close();
    console.log("✅ 인쇄 완료");

    res.json({
      success: true,
      message: "인쇄 완료",
      typeCode: typeCode,
    });
  } catch (err) {
    console.error("❌ 인쇄 실패:", err);
    if (device) {
      try {
        await device.close();
      } catch (e) {
        console.error("프린터 종료 오류:", e);
      }
    }
    res.status(500).json({
      success: false,
      message: "인쇄 실패: " + err.message,
    });
  }
});

app.listen(PORT, () => {
  console.log(`
╔═══════════════════════════════════════════╗
║   🖨️  프린터 서버 실행 중                  ║
╠═══════════════════════════════════════════╣
║   포트: ${PORT}                            ║
║   프린트 API: http://localhost:${PORT}/api/print
║                                           ║
║   ✅ 프린터 연결 대기 중...                ║
╚═══════════════════════════════════════════╝
  `);
});
