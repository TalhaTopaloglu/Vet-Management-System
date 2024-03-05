package com.vet.Vet.api;

import com.vet.Vet.buisness.abstracts.IAnimalService;
import com.vet.Vet.buisness.abstracts.IAppointmentService;
import com.vet.Vet.buisness.abstracts.IDoctorService;
import com.vet.Vet.core.exception.NotFoundException;
import com.vet.Vet.core.result.Result;
import com.vet.Vet.core.result.ResultData;
import com.vet.Vet.core.utilies.ResultHelper;
import com.vet.Vet.dto.request.appointment.AppointmentSaveRequest;
import com.vet.Vet.dto.request.appointment.AppointmentUpdateRequest;
import com.vet.Vet.dto.response.CursorResponse;
import com.vet.Vet.dto.response.appointment.AppointmentResponse;
import com.vet.Vet.entities.Animal;
import com.vet.Vet.entities.Doctor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/appointments")
public class AppointmentController {

    private final IAppointmentService appointmentService;
    private final IDoctorService doctorService;

    public AppointmentController(IAppointmentService appointmentService, IDoctorService doctorService, IAnimalService animalService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.animalService = animalService;
    }

    private final IAnimalService animalService;



    //Değerlendirme Formu 17
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AppointmentResponse> save(@Valid @RequestBody AppointmentSaveRequest appointmentSaveRequest){
        return ResultHelper.created(this.appointmentService.save(appointmentSaveRequest));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> get (@PathVariable("id") int id){
        return ResultHelper.success(this.appointmentService.get(id));
    }
    //Değerlendirme Formu 20
    @GetMapping("/doctor/{doctorId}/start-date/{startDateTime}/end-date/{finishDateTime}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse>> getDoctorAppointments(
            @PathVariable("doctorId") int doctorId,
            @PathVariable("startDateTime") LocalDateTime startDateTime,
            @PathVariable("finishDateTime") LocalDateTime finishDateTime
    ) {
        Doctor doctor = this.doctorService.getOne(doctorId);
        if(startDateTime.isAfter(finishDateTime)){
            throw new NotFoundException("Başlangıç tarihi bitiş tarihinden sonra olmalı");
        }
        if(!this.appointmentService.getDoctorsAppointment(startDateTime,finishDateTime,doctor).isEmpty()){
            return ResultHelper.success(this.appointmentService.getDoctorsAppointment(startDateTime,finishDateTime,doctor));
        }
        throw new NotFoundException(startDateTime.toString() + " ile " + finishDateTime.toString() + " tarihleri arasında " + doctor.getName() + " isimli doktorun randevusu bulunmamaktadır!");
    }
    //Değerlendirme Formu 19
    @GetMapping("/animal/{animalId}/start-date/{startDateTime}/end-date/{finishDateTime}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse>> getAnimalAppointments(
            @PathVariable("animalId") int animalId,
            @PathVariable("startDateTime") LocalDateTime startDateTime,
            @PathVariable("finishDateTime") LocalDateTime finishDateTime
    ) {
        Animal animal = this.animalService.getOne(animalId);
        if(startDateTime.isAfter(finishDateTime)){
            throw new NotFoundException("başlangıç tarihi bitiş tarihinden sonra olmalı");
        }
        if(!this.appointmentService.getAnimalAppointments(startDateTime,finishDateTime,animal).isEmpty()){
            return ResultHelper.success(this.appointmentService.getAnimalAppointments(startDateTime,finishDateTime,animal));
        }
        throw new NotFoundException(startDateTime.toString() + " ile " + finishDateTime.toString() + " tarihleri arasında " + animal.getName() + " isimli hayvanın randevusu bulunmamaktadır!");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AppointmentResponse>> cursor(
            @RequestParam(name = "size",required = false,defaultValue = "0") int page,
            @RequestParam(name = "pageSize",required = false,defaultValue = "10") int pageSize
    ){
        return ResultHelper.cursor(this.appointmentService.cursor(page, pageSize));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> update(@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest){
        this.appointmentService.getOne(appointmentUpdateRequest.getId());
        return ResultHelper.success(this.appointmentService.update(appointmentUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        this.appointmentService.delete(id);
        return ResultHelper.ok();
    }
}
