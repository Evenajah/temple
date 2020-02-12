package com.cdgs.temple.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdgs.temple.dto.CourseDto;
import com.cdgs.temple.dto.MemberDto;
import com.cdgs.temple.dto.MembersHasCourseDto;
import com.cdgs.temple.service.CourseService;
import com.cdgs.temple.service.MemberService;
import com.cdgs.temple.service.MembersHasCourseService;
import com.cdgs.temple.util.ResponseDto;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/excel")
public class ExcelController {

	private MemberService memberService;
	private MembersHasCourseService membersHasCourseService;
	private CourseService courseService;

	@GetMapping("/download")
	public void downloadFile(String fileName, HttpServletResponse res) throws Exception {
		res.setHeader("Content-Disposition", "attacment; filename=" + fileName);
		res.getOutputStream().write(contentOf(fileName));

	}

	private byte[] contentOf(String fileName) throws Exception {

		return Files.readAllBytes(Paths.get("D:\\templeFile/" + fileName));
	}

	@Autowired
	public ExcelController(MemberService memberService, MembersHasCourseService membersHasCourseService,
			CourseService courseService) {
		this.memberService = memberService;
		this.membersHasCourseService = membersHasCourseService;
		this.courseService = courseService;
	}

	@GetMapping(path = "")
	public ResponseEntity<ResponseDto<MembersHasCourseDto>> createExcel(
			@RequestParam(value = "courseId") Long courseId) {
		ResponseDto<MembersHasCourseDto> res = new ResponseDto<>();
		int arrayLength = 0;
		int index = 0;
		int bodyStRow = 5;
		int numberOfMember = 1;
		String path = "D:\\templeFile"; // สร้างไฟล์ไว้ที่ไหน.
		try {
			List<MembersHasCourseDto> memberHasCourse = membersHasCourseService.getMembersByCourse(courseId);
			CourseDto course = courseService.getCourse(courseId);
			arrayLength = memberHasCourse.size();

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Sheet1");
			// Page Header
			Header header = sheet.getHeader();
			header.setCenter(HSSFHeader.font("TH SarabunPSK", "regular") + HSSFHeader.fontSize((short) 16)
					+ HSSFHeader.startBold() + "รายชื่อผู้เข้าร่วมปฏิบัติธรรม" + HSSFHeader.endBold());
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			header.setRight(HSSFHeader.font("TH SarabunPSK", "regular") + HSSFHeader.fontSize((short) 16)
					+ "วันที่พิมพ์ " + formatter.format(date));
			Row rowPageHeading = sheet.createRow(0);
			Cell cellPageHeading = rowPageHeading.createCell(3);
			cellPageHeading.setCellValue("คอร์ส " + course.getName());
			cellPageHeading.setCellStyle(getPageHeader(workbook));
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 6));
			rowPageHeading = sheet.createRow(1);
			cellPageHeading = rowPageHeading.createCell(3);

			if (course.getStDate() != null && course.getEndDate() != null) {
				cellPageHeading.setCellValue("วันที่ " + formatter.format(course.getStDate()) + " - "
						+ formatter.format(course.getEndDate()));
			} else {
				cellPageHeading.setCellValue("-");
			}

			cellPageHeading.setCellStyle(getPageHeader(workbook));
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 6));

			rowPageHeading = sheet.createRow(2);
			cellPageHeading = rowPageHeading.createCell(0);
			cellPageHeading.setCellValue("ผู้สอน : พระอาจารย์.........");
			HSSFCellStyle style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.LEFT);
			style.setFont(getFontPageHeader(workbook));
			cellPageHeading.setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));

			cellPageHeading = rowPageHeading.createCell(6);
			cellPageHeading.setCellValue("จำนวนผู้เข้าอบรม " + arrayLength + " คน");
			style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.RIGHT);
			style.setFont(getFontPageHeader(workbook));
			cellPageHeading.setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 6, 8));

			// Table Header
			Row rowHeading = sheet.createRow(3);
			Cell cell = rowHeading.createCell(0);
			cell.setCellValue("ลำดับ");
			// style column ลำดับ Row 3
			cell.setCellStyle(getTableHeader(workbook));
			sheet.addMergedRegion(new CellRangeAddress(3, 4, 0, 0));

			cell = rowHeading.createCell(1);
			cell.setCellValue("ชื่อ-นามสกุล");
			// style column ชื่อ-นามสกุล Row 3
			cell.setCellStyle(getTableHeader(workbook));
			cell = rowHeading.createCell(2);
			cell.setCellStyle(getTableHeader(workbook));
			sheet.addMergedRegion(new CellRangeAddress(3, 4, 1, 2));

			cell = rowHeading.createCell(3);
			cell.setCellValue("เบอร์โทรศัพท์");
			// style column เบอร์โทรศัพท์ Row 3
			cell.setCellStyle(getTableHeader(workbook));
			cell = rowHeading.createCell(4);
			cell.setCellStyle(getTableHeader(workbook));
			sheet.addMergedRegion(new CellRangeAddress(3, 4, 3, 4));

			cell = rowHeading.createCell(5);
			cell.setCellValue("ลงลายมือชื่อ");
			cell.setCellStyle(getTableHeader(workbook));
			cell = rowHeading.createCell(6);
			cell.setCellStyle(getTableHeader(workbook));
			cell = rowHeading.createCell(7);
			cell.setCellStyle(getTableHeader(workbook));
			cell = rowHeading.createCell(8);
			cell.setCellStyle(getTableHeader(workbook));
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 5, 8));

			rowHeading = sheet.createRow(4);
			cell = rowHeading.createCell(5);

			if (course.getStDate() != null) {
				cell.setCellValue("วันที่ " + formatter.format(course.getStDate()));
			} else {
				cell.setCellValue("-");
			}

			cell.setCellStyle(getTableHeader(workbook));
			cell = rowHeading.createCell(6);
			cell.setCellStyle(getTableHeader(workbook));
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 5, 6));

			cell = rowHeading.createCell(7);

			if (course.getEndDate() != null) {
				cell.setCellValue("วันที่ " + formatter.format(course.getEndDate()));
			} else {
				cell.setCellValue("-");
			}

			cell.setCellStyle(getTableHeader(workbook));
			cell = rowHeading.createCell(8);
			cell.setCellStyle(getTableHeader(workbook));
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 7, 8));

			// style column ลำดับ Row 4
			cell = rowHeading.createCell(0);
			cell.setCellStyle(getTableHeader(workbook));

			// style column ชื่อ-นามสกุล Row 4
			cell = rowHeading.createCell(1);
			cell.setCellStyle(getTableHeader(workbook));
			cell = rowHeading.createCell(2);
			cell.setCellStyle(getTableHeader(workbook));

			// style column เบอร์โทรศัพท์ Row 4
			cell = rowHeading.createCell(3);
			cell.setCellStyle(getTableHeader(workbook));
			cell = rowHeading.createCell(4);
			cell.setCellStyle(getTableHeader(workbook));

			// Table Body
			if (arrayLength != 0) {
				for (MembersHasCourseDto member : memberHasCourse) {
					MemberDto memberId = memberService.getMember(member.getMemberId());
					Row rowBody = sheet.createRow(bodyStRow);
					HSSFCellStyle tableBodyIndex = getTableBodyIndex(workbook);
					if (numberOfMember == arrayLength) {
						tableBodyIndex.setBorderBottom(BorderStyle.THIN);
						tableBodyIndex.setBottomBorderColor(IndexedColors.BLACK.getIndex());
					}
					if (numberOfMember % 2 == 0) {
						tableBodyIndex.setFillForegroundColor(IndexedColors.fromInt(22).getIndex());
						tableBodyIndex.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					}
					cell = rowBody.createCell(0);
					cell.setCellValue(++index);
					cell.setCellStyle(tableBodyIndex);

					HSSFCellStyle tableBody = getTableBody(workbook);
					if (numberOfMember == arrayLength) {
						tableBody.setBorderBottom(BorderStyle.THIN);
						tableBody.setBottomBorderColor(IndexedColors.BLACK.getIndex());
					}
					if (numberOfMember % 2 == 0) {
						tableBody.setFillForegroundColor(IndexedColors.fromInt(22).getIndex());
						tableBody.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					}
					cell = rowBody.createCell(1);
					cell.setCellValue(memberId.getTitleName() + memberId.getFname() + " " + memberId.getLname());
					cell.setCellStyle(tableBody);
					cell = rowBody.createCell(2);
					tableBody.setBorderTop(BorderStyle.THIN);
					tableBody.setTopBorderColor(IndexedColors.BLACK.getIndex());
					tableBody.setBorderRight(BorderStyle.THIN);
					tableBody.setRightBorderColor(IndexedColors.BLACK.getIndex());
					cell.setCellStyle(tableBody);
					sheet.addMergedRegion(new CellRangeAddress(bodyStRow, bodyStRow, 1, 2));

					cell = rowBody.createCell(3);
					cell.setCellValue(memberId.getTel());
					cell.setCellStyle(tableBody);
					cell = rowBody.createCell(4);
					cell.setCellStyle(tableBody);
					sheet.addMergedRegion(new CellRangeAddress(bodyStRow, bodyStRow, 3, 4));

					sheet.addMergedRegion(new CellRangeAddress(bodyStRow, bodyStRow, 5, 6));
					cell = rowBody.createCell(5);
					cell.setCellStyle(tableBody);
					cell = rowBody.createCell(6);
					cell.setCellStyle(tableBody);

					sheet.addMergedRegion(new CellRangeAddress(bodyStRow, bodyStRow, 7, 8));
					HSSFCellStyle tableBodyLastColumn = getTableBodyLastColumn(workbook);
					if (numberOfMember == arrayLength) {
						tableBodyLastColumn.setBorderBottom(BorderStyle.THIN);
						tableBodyLastColumn.setBottomBorderColor(IndexedColors.BLACK.getIndex());
					}
					if (numberOfMember % 2 == 0) {
						tableBodyLastColumn.setFillForegroundColor(IndexedColors.fromInt(22).getIndex());
						tableBodyLastColumn.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					}
					cell = rowBody.createCell(7);
					cell.setCellStyle(tableBodyLastColumn);
					cell = rowBody.createCell(8);
					cell.setCellStyle(tableBodyLastColumn);

					bodyStRow++;
					numberOfMember++;
				}
			}

			// Page Footer
			Footer footer = sheet.getFooter();
			footer.setLeft(HSSFHeader.font("TH SarabunPSK", "regular") + HSSFHeader.fontSize((short) 16)
					+ "วันที่พิมพ์ " + formatter.format(date));
			footer.setRight(HSSFHeader.font("TH SarabunPSK", "regular") + HSSFHeader.fontSize((short) 16)
					+ "วัดป่าโสมพนัส " + course.getLocationName());

			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
				dir.createNewFile();
				System.out.println("Create newfile");
			}

//            File file = new File(path);
//            file.getParentFile().mkdirs();

			OutputStream out = new FileOutputStream(path + "/temple.xls");
			workbook.write(out);
			out.close();
			workbook.close();

			// คำสั่งเปิดไฟล์จากเครื่องตัวเอง
			// ไม่แน่ใจว่าเปิดจากเครื่องเซิฟจะใช้ได้หรือเปล่า?
//            Runtime.getRuntime().exec("cmd /c start "+path);
			System.out.println("Create end");
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(memberHasCourse);
			res.setCode(201);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	// Excel Style
	// Font
	public Font getFontPageHeader(HSSFWorkbook workbook) {
		Font fontPageHeader = workbook.createFont();
		fontPageHeader.setFontHeightInPoints((short) 16);
		fontPageHeader.setFontName("TH SarabunPSK");
		fontPageHeader.setBold(true);
		return fontPageHeader;
	}

	public Font getFontText(HSSFWorkbook workbook) {
		Font fontText = workbook.createFont();
		fontText.setFontHeightInPoints((short) 16);
		fontText.setFontName("TH SarabunPSK");
		return fontText;
	}

	// Style Page Header
	public HSSFCellStyle getPageHeader(HSSFWorkbook workbook) {
		HSSFCellStyle pageHeader = workbook.createCellStyle();
		pageHeader.setAlignment(HorizontalAlignment.CENTER);
		pageHeader.setFont(getFontPageHeader(workbook));
		return pageHeader;
	}

	// Style Table Header
	public HSSFCellStyle getTableHeader(HSSFWorkbook workbook) {
		HSSFCellStyle tableHeader = workbook.createCellStyle();
		tableHeader.setAlignment(HorizontalAlignment.CENTER);
		tableHeader.setFont(getFontText(workbook));
		tableHeader.setVerticalAlignment(VerticalAlignment.CENTER);
		tableHeader.setBorderTop(BorderStyle.THIN);
		tableHeader.setTopBorderColor(IndexedColors.BLACK.getIndex());
		tableHeader.setBorderBottom(BorderStyle.THIN);
		tableHeader.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		tableHeader.setBorderRight(BorderStyle.THIN);
		tableHeader.setRightBorderColor(IndexedColors.BLACK.getIndex());
		tableHeader.setBorderLeft(BorderStyle.THIN);
		tableHeader.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		return tableHeader;
	}

	// Style Table Body
	public HSSFCellStyle getTableBody(HSSFWorkbook workbook) {
		HSSFCellStyle tableBody = workbook.createCellStyle();
		tableBody.setFont(getFontText(workbook));
		return tableBody;
	}

	public HSSFCellStyle getTableBodyIndex(HSSFWorkbook workbook) {
		HSSFCellStyle tableBodyIndex = workbook.createCellStyle();
		tableBodyIndex.setFont(getFontText(workbook));
		tableBodyIndex.setBorderTop(BorderStyle.THIN);
		tableBodyIndex.setTopBorderColor(IndexedColors.BLACK.getIndex());
		tableBodyIndex.setBorderLeft(BorderStyle.THIN);
		tableBodyIndex.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		tableBodyIndex.setBorderRight(BorderStyle.THIN);
		tableBodyIndex.setRightBorderColor(IndexedColors.BLACK.getIndex());
		tableBodyIndex.setAlignment(HorizontalAlignment.CENTER);
		return tableBodyIndex;
	}

	public HSSFCellStyle getTableBodyLastColumn(HSSFWorkbook workbook) {
		HSSFCellStyle tableBodyLastColumn = workbook.createCellStyle();
		tableBodyLastColumn.setFont(getFontText(workbook));
		tableBodyLastColumn.setBorderTop(BorderStyle.THIN);
		tableBodyLastColumn.setTopBorderColor(IndexedColors.BLACK.getIndex());
		tableBodyLastColumn.setBorderRight(BorderStyle.THIN);
		tableBodyLastColumn.setRightBorderColor(IndexedColors.BLACK.getIndex());
		return tableBodyLastColumn;
	}
}
