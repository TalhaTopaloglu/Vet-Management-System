package com.vet.Vet.api;

import com.vet.Vet.buisness.abstracts.IVaccineService;
import com.vet.Vet.core.config.modelMapper.IModelMapperService;
import com.vet.Vet.core.exception.NotFoundException;
import com.vet.Vet.core.result.Result;
import com.vet.Vet.core.result.ResultData;
import com.vet.Vet.core.utilies.ResultHelper;
import com.vet.Vet.dto.request.vaccine.VaccineSaveRequest;
import com.vet.Vet.dto.request.vaccine.VaccineUpdateRequest;
import com.vet.Vet.dto.response.CursorResponse;
import com.vet.Vet.dto.response.vaccine.VaccineResponse;
import com.vet.Vet.entities.Vaccine;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController {

    private final IVaccineService vaccineService;

    public VaccineController(IVaccineService vaccineService) {
        this.vaccineService = vaccineService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> save(@Valid @RequestBody VaccineSaveRequest vaccineSaveRequest) {

        return ResultHelper.created(this.vaccineService.save(vaccineSaveRequest));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> get(@PathVariable("id") int id) {
        return ResultHelper.success(this.vaccineService.get(id));
    }

    @GetMapping("/animal/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getByAnimalId(@PathVariable("id") int id) {
        if (!this.vaccineService.getByAnimalId(id).isEmpty()) {
            return ResultHelper.success(this.vaccineService.getByAnimalId(id));
        }
        throw new NotFoundException(id + " ID'li hayvanın aşı bilgisi bulunmamaktadır!");
    }

    @GetMapping("/protection-date/start-date/{protectionStartDate}/end-date/{protectionFinishDate}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getByVaccineProtectionDate(
            @PathVariable("protectionStartDate")LocalDate startDate,
            @PathVariable("protectionFinishDate") LocalDate finishDate
    ) {
        if(startDate.isAfter(finishDate)){
            throw new NotFoundException("Başlangıç tarihi bitiş tarihinden önce olamaz");
        }

        if(!this.vaccineService.getByProtectionFinishDate(startDate,finishDate).isEmpty()){
            return ResultHelper.success(this.vaccineService.getByProtectionFinishDate(startDate,finishDate));
        }
        throw new NotFoundException(startDate.toString() + " ile " + finishDate.toString()   + " tarihleri arasında koruyuculuk bitiş tarihi olan aşı bulunmamaktadır!");
    }


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<VaccineResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ) {
        return ResultHelper.cursor(this.vaccineService.cursor(page, pageSize));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> update(@Valid @RequestBody VaccineUpdateRequest vaccineUpdateRequest) {
        this.vaccineService.getOne(vaccineUpdateRequest.getId());
        return ResultHelper.success(this.vaccineService.update(vaccineUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        this.vaccineService.delete(id);
        return ResultHelper.ok();
    }
}
