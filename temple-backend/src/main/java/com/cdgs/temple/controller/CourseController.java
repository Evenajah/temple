package com.cdgs.temple.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdgs.temple.dto.ApprovalCoursesDto;
import com.cdgs.temple.dto.CourseDto;
import com.cdgs.temple.dto.CourseScheduleDto;
import com.cdgs.temple.dto.CourseTeacherDto;
import com.cdgs.temple.dto.MemberDto;
import com.cdgs.temple.dto.MembersHasCourseDto;
import com.cdgs.temple.dto.ResponseCountDto;
import com.cdgs.temple.dto.SensationDto;
import com.cdgs.temple.dto.SpecialApproveDto;
import com.cdgs.temple.dto.TransportationDto;
import com.cdgs.temple.entity.CourseEntity;
import com.cdgs.temple.service.ApprovalCoursesService;
import com.cdgs.temple.service.CourseScheduleService;
import com.cdgs.temple.service.CourseService;
import com.cdgs.temple.service.CourseTeacherService;
import com.cdgs.temple.service.EmailService;
import com.cdgs.temple.service.MemberService;
import com.cdgs.temple.service.MembersHasCourseService;
import com.cdgs.temple.service.NotificationsService;
import com.cdgs.temple.service.SensationService;
import com.cdgs.temple.service.SpecialApproveService;
import com.cdgs.temple.service.TransportationService;
import com.cdgs.temple.service.impl.EmailServiceImpl;
import com.cdgs.temple.service.impl.NotificationsServiceImpl;
import com.cdgs.temple.util.ResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/courses")
@Slf4j
public class CourseController {
	private TransportationService transportationService;
	private MemberService memberService;
	private CourseService courseService;
	private MembersHasCourseService membersHasCourseService;
	private CourseScheduleService courseScheduleService;
	private CourseTeacherService courseTeacherservice;
	private SpecialApproveService specialApproveService;
	private SensationService sensationService;
	private ApprovalCoursesService approvalCoursesService;
	private NotificationsService notificationsService = new NotificationsServiceImpl();
	private EmailService emailService = new EmailServiceImpl();

	@Autowired
	public CourseController(TransportationService transportationService, MemberService memberService,
			CourseService courseService, MembersHasCourseService membersHasCourseService,
			CourseScheduleService courseScheduleService, CourseTeacherService courseTeacherservice,
			SpecialApproveService specialApproveService, SensationService sensationService,
			ApprovalCoursesService approvalCoursesService) {
		super();
		this.transportationService = transportationService;
		this.memberService = memberService;
		this.courseService = courseService;
		this.membersHasCourseService = membersHasCourseService;
		this.courseScheduleService = courseScheduleService;
		this.courseTeacherservice = courseTeacherservice;
		this.specialApproveService = specialApproveService;
		this.sensationService = sensationService;
		this.approvalCoursesService = approvalCoursesService;
	}

	@GetMapping(path = "/allmembers/{id}")
	@PreAuthorize("hasRole('admin') or hasRole('monk') or hasRole('user')")
	public ResponseEntity<ResponseDto<MembersHasCourseDto>> getAllUsers(@PathVariable(value = "id") Long courseId) {
		ResponseDto<MembersHasCourseDto> res = new ResponseDto<>();
		try {
			List<MembersHasCourseDto> memberHasCourse = membersHasCourseService.getMembersByCourse(courseId);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());

			res.setData(memberHasCourse);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/user")
	@PreAuthorize("hasRole('user')")
	public ResponseEntity<ResponseDto<CourseDto>> getCoursesUser(@RequestParam("status") String status) {
		ResponseDto<CourseDto> res = new ResponseDto<>();
		List<CourseDto> dto = new ArrayList<>();
		MemberDto member = memberService.getCurrentMember();
		try {
			if (status.equals("0")) {
				dto = courseService.getCoursesUserRegister(member.getId());
			} else {
				dto = courseService.getCoursesUser(member.getId(), status);
			}
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("catch >> getCoursesUser ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * GetCourses this function for get courses by admin and monk
	 * 
	 */
	@GetMapping(path = "")
	@PreAuthorize("hasRole('admin') or hasRole('monk')")
	public ResponseEntity<ResponseDto<CourseDto>> getCourses() {
		ResponseDto<CourseDto> res = new ResponseDto<>();
		List<CourseDto> dto = new ArrayList<>();
		MemberDto member = memberService.getCurrentMember();
		try {
			if (member.getRoleName().equals("monk")) {
				dto = courseService.getCoursesMonk(member.getId());
			} else {
				dto = courseService.getCourses();
			}
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("catch >> getCourses ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/count")
	@PreAuthorize("hasRole('admin') or hasRole('monk') or hasRole('user')")
	public ResponseEntity<ResponseDto<ResponseCountDto>> countCourses(@RequestParam("status") String status) {
		ResponseDto<ResponseCountDto> res = new ResponseDto<>();
		List<ResponseCountDto> listDto = new ArrayList<>();
		ResponseCountDto dto = new ResponseCountDto();
		int count = 0;
		MemberDto member = memberService.getCurrentMember();
		try {
			if (member.getRoleName().equals("user")) {
				if (status.equals("0")) {
					count = courseService.countUserRegisterCourses(member.getId());
				} else if (status.equals("2") || status.equals("1")) {
					count = courseService.countUserCourses(member.getId(), status);
				}
			} else if (member.getRoleName().equals("monk")) {
				count = courseService.countMonkCourses(member.getId());
			} else {
				count = courseService.countCourses();
			}
			dto.setTotalRecord(count);
			listDto.add(dto);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(listDto);
			res.setCode(201);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("catch >> countCourses ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/approve")
	@PreAuthorize("hasRole('monk')")
	public ResponseEntity<ResponseDto<ApprovalCoursesDto>> teacherGetCoursesApproval(@RequestParam("offset") int offset,
			@RequestParam("limit") int limit, @RequestParam("query") String query) {
		ResponseDto<ApprovalCoursesDto> res = new ResponseDto<>();
		List<ApprovalCoursesDto> dto;
		MemberDto member = memberService.getCurrentMember();

		try {
			// เปลี่ยนไปใช้ ApprovalCourses จากเดิม Course (TeacherGetCoursesApproval)
			// เพิ่มจำนวนนักเรียนในแต่ละคอร์ส
			dto = approvalCoursesService.getApprovalCourses(query, member.getId(), limit, offset);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("catch >> teacherGetCoursesApproval ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/approve/outTime")
	@PreAuthorize("hasRole('monk')")
	public ResponseEntity<ResponseDto<CourseDto>> teacherGetCoursesOutTime(@RequestParam("offset") int offset,
			@RequestParam("limit") int limit, @RequestParam("query") String query) {
		ResponseDto<CourseDto> res = new ResponseDto<>();
		List<CourseDto> dto;
		MemberDto member = memberService.getCurrentMember();

		try {
			// เปลี่ยนไปใช้ ApprovalCourses จากเดิม Course (TeacherGetCoursesApproval)
			// เพิ่มจำนวนนักเรียนในแต่ละคอร์ส
			dto = courseService.TeacherGetCoursesApprovalOutTime(member.getId(), offset, limit, query);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("catch >> teacherGetCoursesOutTime ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/approve/count")
	@PreAuthorize("hasRole('monk')")
	public ResponseEntity<ResponseDto<ResponseCountDto>> countTeacherCoursesApproval() {
		ResponseDto<ResponseCountDto> res = new ResponseDto<>();
		List<ResponseCountDto> listDto = new ArrayList<>();
		ResponseCountDto dto = new ResponseCountDto();
		int count;
		MemberDto member = memberService.getCurrentMember();
		try {
			count = courseService.CountTeacherCoursesApproval(member.getId());
			dto.setTotalRecord(count);
			listDto.add(dto);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(listDto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("countTeacherCoursesApproval ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/approve/outTime/count")
	@PreAuthorize("hasRole('monk')")
	public ResponseEntity<ResponseDto<ResponseCountDto>> countTeacherCoursesApprovalOutTime() {
		ResponseDto<ResponseCountDto> res = new ResponseDto<>();
		List<ResponseCountDto> listDto = new ArrayList<>();
		ResponseCountDto dto = new ResponseCountDto();
		int count;
		MemberDto member = memberService.getCurrentMember();
		try {
			count = courseService.CountTeacherCoursesApprovalOutTime(member.getId());
			dto.setTotalRecord(count);
			listDto.add(dto);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(listDto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * getCoursesById this function for get courses by ID params: id of row
	 */
	@GetMapping(path = "/{id}")
	@PreAuthorize("hasRole('admin') or hasRole('monk') or hasRole('user')")
	public ResponseEntity<ResponseDto<CourseDto>> getCoursesById(@PathVariable("id") Long id) {
		ResponseDto<CourseDto> res = new ResponseDto<>();
		MemberDto member = memberService.getCurrentMember();
		CourseDto courseDto = new CourseDto();
		List<CourseDto> courseDtoList = new ArrayList<>();
		try {
			if (member.getRoleName().equals("user")) {
				courseDto = courseService.getCourseUser(member.getId(), id);
				courseDto.setTeacherList(memberService.getAllUsersWithOutImg());
				if (courseDto.getTransportation() == null) {
					TransportationDto transportationDto;
					if (courseDto.getNo() == Long.parseLong("0")) {
						transportationDto = transportationService.getTransportationByCourseId(id);
					} else {
						SpecialApproveDto specialApproveDto;
						specialApproveDto = specialApproveService.getByCourseIdAndMemberId(courseDto.getId(),
								member.getId());
						transportationDto = transportationService
								.getTransportationById(specialApproveDto.getTransportationId());
					}
					courseDto.setTransportation(transportationDto);
				}
			} else {
				courseDto = courseService.getCourse(id);
				if (courseDto.getTransportation() == null) {
					courseDto.setTransportation(transportationService.getTransportationByCourseId(id));
					if (courseDto.getTransportation().getId() == null) {
						courseDto.setTransportation(null);
					}
				}
			}

			List<MemberDto> listMemDto = memberService.getMemberByCourseId(courseDto.getId());

			List<Long> listTeacher = new ArrayList<>();
			List<MemberDto> listTeacherDto = new ArrayList<>();
			for (MemberDto memberDto : listMemDto) {
				listTeacher.add(memberDto.getId());
				listTeacherDto.add(memberDto);
			}
			courseDto.setTeacher(listTeacher);
			courseDto.setTeacherList(listTeacherDto);
			courseDtoList.add(courseDto);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(courseDtoList);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("getCoursesById ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/outTime")
	@PreAuthorize("hasRole('user')")
	public ResponseEntity<ResponseDto<CourseDto>> getCoursesOutTime() {
		ResponseDto<CourseDto> res = new ResponseDto<>();
		List<CourseDto> listCourseDto;
		MemberDto member = memberService.getCurrentMember();
		try {
			listCourseDto = courseService.getCoursesOutTime(member.getId());
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(listCourseDto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("getCoursesOutTime ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/getPassCourse/{id}")
	public ResponseEntity<ResponseDto<Long>> getMemberPassCourse(@PathVariable("id") Long id) {
		ResponseDto<Long> res = new ResponseDto<>();
		List<Long> dto = new ArrayList<>();
		Long passCourse = null;
		try {
			passCourse = membersHasCourseService.countForPassCourses(id);
			dto.add(passCourse);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("getMemberPassCourse ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * createCourse this function is create new course by admin
	 * 
	 */
	@PostMapping(path = "")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<ResponseDto<CourseDto>> createCourse(@Valid @RequestBody CourseDto body) {
		ResponseDto<CourseDto> res = new ResponseDto<>();
		List<CourseDto> courses = new ArrayList<>();
		CourseDto course;
		CourseScheduleDto courseSchedule = new CourseScheduleDto();
		CourseTeacherDto courseTeacher = new CourseTeacherDto();
		TransportationDto transportationDto = new TransportationDto();
		Date dateSt = body.getDate().get(0);
		Date dateEnd = body.getDate().get(1);
		try {
			course = courseService.createCourse(body);
			courseSchedule.setCourseId(course.getId());
			if (body.getTransportation().getId() != null) {
				transportationDto.setCourseId(course.getId());
				transportationDto.setId(body.getTransportation().getId());
				transportationService.updateTransportationTemple(transportationDto.getId(), transportationDto);
			}
			// ตรวจสอบกรณีวันที่เท่ากัน / 1 วัน
			if (dateSt.compareTo(dateEnd) == 0) {
				courseSchedule.setCourseScheduleDate(body.getDate().get(0));
				courseScheduleService.createCourseSchedule(courseSchedule);
			} else {
				for (Date date : body.getDate()) {
					courseSchedule.setCourseScheduleDate(date);
					courseScheduleService.createCourseSchedule(courseSchedule);
				}
			}
			courseTeacher.setCourseId(course.getId());
			for (Long tId : body.getTeacher()) {
				courseTeacher.setMemberId(tId);
				courseTeacher = courseTeacherservice.createCourseTeacher(courseTeacher);

			}
			courses.add(course);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(courses);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("createCourse ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/outTime")
	public ResponseEntity<ResponseDto<CourseDto>> assignCoursesOutTime(@Valid @RequestBody SpecialApproveDto body) {
		ResponseDto<CourseDto> res = new ResponseDto<>();
		CourseDto courseDto = new CourseDto();
		CourseDto courseOutTimeDto = new CourseDto();
		CourseTeacherDto courseTeacherDto = new CourseTeacherDto();
		List<CourseDto> listDto = new ArrayList<>();
		MemberDto member = memberService.getCurrentMember();
		try {
			// create CourseOutTime
			courseDto = courseService.getCourseById(body.getCourseId());
			courseDto.setId(null);
			courseDto.setNo(body.getCourseId());
			courseDto.setStatus("0");
			courseDto.setStDate(body.getStDate());
			courseDto.setEndDate(body.getEndDate());
			courseOutTimeDto = courseService.createCourse(courseDto);
			courseTeacherDto.setCourseId(courseOutTimeDto.getId());
			for (Long tId : courseDto.getTeacher()) {
				courseTeacherDto.setMemberId(tId);
				courseTeacherDto = courseTeacherservice.createCourseTeacher(courseTeacherDto);
			}
			body.setCourseId(courseOutTimeDto.getId());
			body.setStatus("4");
			body.setMemberId(member.getId());
			SpecialApproveDto spaDto = specialApproveService.create(body);
			listDto.add(courseOutTimeDto);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(listDto);
			res.setCode(200);

			// เพิ่มข้อมูล notification ไปให้ ผู้สอน
			notificationsService.createMonkNotifications(courseDto.getTeacher(), spaDto.getSpecialApproveId(),
					courseOutTimeDto.getId(), spaDto.getStatus(), courseOutTimeDto.getName());

			String subject = "รายงานการสมัครคอร์ส";
			String text = "คอร์ส " + courseOutTimeDto.getName() + " ได้รับการอนุมัติแล้ว";

			// ส่ง email ไปให้ผู้สอน
			for (Long teacherId : courseDto.getTeacher()) {

				MemberDto teacher = memberService.getMember(teacherId);

				if (null != teacher.getEmail()) {
					emailService.sendEmail(teacher.getEmail(), subject, text);
				}
			}

			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("assignCoursesOutTime ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * putCourse this function for update course by admin. params: id of row and
	 * body: CourseDto
	 */
	@PutMapping(path = "/{id}")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<ResponseDto<CourseDto>> putCourse(@PathVariable("id") Long id,
			@Valid @RequestBody CourseDto body) {
		ResponseDto<CourseDto> res = new ResponseDto<>();
		List<CourseDto> courses = new ArrayList<>();
		CourseTeacherDto courseTeacher = new CourseTeacherDto();
		CourseDto course;
		CourseScheduleDto courseSchedule = new CourseScheduleDto();
		try {
			course = courseService.updateCourse(id, body);
			courses.add(body);
			/* ตรวจสอบเพิ่มกรณีไม่มีข้อมูลใน schedule */
			if (courseScheduleService.getCourseId(id) == null) {
				courseSchedule.setCourseId(course.getId());
				for (Date date : body.getDate()) {
					courseSchedule.setCourseScheduleDate(date);
					courseScheduleService.createCourseSchedule(courseSchedule);
				}
			}
			courseTeacher.setCourseId(course.getId());
			for (Long tId : body.getTeacher()) {
				courseTeacher.setMemberId(tId);
				courseTeacher = courseTeacherservice.createCourseTeacher(courseTeacher);
			}

			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(courses);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("putCourse ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(path = "/{id}")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<ResponseDto<CourseEntity>> deleteCourse(@PathVariable("id") long id) {
		ResponseDto<CourseEntity> res = new ResponseDto<>();
		try {
			if (Boolean.TRUE.equals(courseService.deleteCourse(id))) {
				res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
				res.setCode(204);
				return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
			} else {
				res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
				res.setCode(200);
				return new ResponseEntity<>(res, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("deleteCourse ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/register")
	@PreAuthorize("hasRole('user')")
	public ResponseEntity<ResponseDto<MembersHasCourseDto>> createMemberHasCourse(
			@Valid @RequestBody MembersHasCourseDto body) {
		ResponseDto<MembersHasCourseDto> res = new ResponseDto<>();
		List<MembersHasCourseDto> courses = new ArrayList<>();
		MembersHasCourseDto memberHasCourse;
		MemberDto member = memberService.getCurrentMember();
		SensationDto tempSensation = new SensationDto();
		SensationDto sensation = new SensationDto();

		try {
			if (courseService.getCoursesUserByCourseId(member.getId(), body.getCourseId()) == 0) {
				tempSensation.setId(body.getSenseId());
				tempSensation.setExpected(body.getExpected());
				tempSensation.setExperience(body.getExperience());
				sensation = sensationService.createSensation(tempSensation);

				body.setMemberId(member.getId());
				body.setSenseId(sensation.getId());
				body.setStatus('2');
				memberHasCourse = courseService.assignCourse(body);
				if (memberHasCourse == null) {
					throw new Exception("เงื่อนไขการสมัครไม่ถูกต้อง");
				}

				courses.add(memberHasCourse);
				res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
				res.setData(courses);
				res.setCode(200);
				return new ResponseEntity<>(res, HttpStatus.OK);
			} else {
				res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
				res.setErrorMessage("Duplicate register");
				res.setCode(200);
				return new ResponseEntity<>(res, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("createMemberHasCourse ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/schedule")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<ResponseDto<CourseScheduleDto>> postCourseSchedule(
			@Valid @RequestBody CourseScheduleDto courseSchedule) {
		ResponseDto<CourseScheduleDto> res = new ResponseDto<>();
		List<CourseScheduleDto> courseSchedules = new ArrayList<>();
		CourseScheduleDto dto;
		try {
			dto = courseScheduleService.createCourseSchedule(courseSchedule);
			courseSchedules.add(dto);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(courseSchedules);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/schedule")
	@PreAuthorize("hasRole('user')")
	public ResponseEntity<ResponseDto<CourseScheduleDto>> getCourseScheduleByUser() {
		ResponseDto<CourseScheduleDto> res = new ResponseDto<>();
		List<CourseScheduleDto> dto;
		MemberDto member = memberService.getCurrentMember();
		try {
			dto = courseScheduleService.getCourseScheduleByUser(member.getId());
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("getCourseScheduleByUser ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/teacher_schedule")
	@PreAuthorize("hasRole('monk')")
	public ResponseEntity<ResponseDto<CourseScheduleDto>> getCourseScheduleByMonk() {
		ResponseDto<CourseScheduleDto> res = new ResponseDto<>();
		List<CourseScheduleDto> dto;
		MemberDto member = memberService.getCurrentMember();
		try {
			dto = courseScheduleService.getCourseScheduleByMonk(member.getId());
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(path = "/deleteCourse/{id}")
	@PreAuthorize("hasRole('monk')")
	public ResponseEntity<ResponseDto<CourseDto>> deleteCourse(@PathVariable("id") Long id) {
		ResponseDto<CourseDto> res = new ResponseDto<>();
		try {
			if (Boolean.TRUE.equals(courseService.deleteCourse(id))) {
				res.setResult("Success");
				res.setCode(200);
				return new ResponseEntity<>(res, HttpStatus.OK);
			} else {
				res.setResult("Fail");
				res.setCode(400);
				return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/history/{id}")
	@PreAuthorize("hasRole('admin') or hasRole('monk') or hasRole('user')")
	public ResponseEntity<ResponseDto<CourseDto>> getHistoryCourseLearn(@PathVariable("id") Long id) {
		ResponseDto<CourseDto> res = new ResponseDto<>();
		List<CourseDto> dto = new ArrayList<>();
		MemberDto member = memberService.getCurrentMember();
		try {
			if (member.getRoleName().equals("user")) {
				dto = courseService.getHistory(member.getId());
			} else {
				dto = courseService.getHistory(id);
			}
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("getHistoryCourseLearn ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}
}
