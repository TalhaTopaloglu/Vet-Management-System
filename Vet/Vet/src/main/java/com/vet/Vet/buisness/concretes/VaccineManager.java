package com.vet.Vet.buisness.concretes;

import com.vet.Vet.buisness.abstracts.IAnimalService;
import com.vet.Vet.buisness.abstracts.IVaccineService;
import com.vet.Vet.core.config.modelMapper.IModelMapperService;
import com.vet.Vet.core.exception.NotFoundException;
import com.vet.Vet.core.utilies.Msg;
import com.vet.Vet.dao.VaccineRepo;
import com.vet.Vet.dto.request.vaccine.VaccineSaveRequest;
import com.vet.Vet.dto.request.vaccine.VaccineUpdateRequest;
import com.vet.Vet.dto.response.vaccine.VaccineResponse;
import com.vet.Vet.entities.Animal;
import com.vet.Vet.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VaccineManager implements IVaccineService {
    private final VaccineRepo vaccineRepo;
    private final IAnimalService animalService;
    private final IModelMapperService modelMapper;

    public VaccineManager(VaccineRepo vaccineRepo, IAnimalService animalService, IModelMapperService modelMapper) {
        this.vaccineRepo = vaccineRepo;
        this.animalService = animalService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Vaccine getOne(int id) {
        return this.vaccineRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public List<VaccineResponse> getByAnimalId(int id) {
        return vaccineRepo.findVaccineByAnimalId(id)
                .stream()
                .map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class)
                ).collect(Collectors.toList());
    }

    @Override
    public List<VaccineResponse> getByProtectionFinishDate(LocalDate protectionStartDate, LocalDate protectionFinishDate) {
        return vaccineRepo.findByProtectionDate(protectionStartDate,protectionFinishDate)
                .stream()
                .map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class)
                ).collect(Collectors.toList());
    }

    @Override
    public VaccineResponse save(VaccineSaveRequest vaccineSaveRequest) {
        Optional<Vaccine> checkVaccine = vaccineRepo.findByNameAndCodeAndAnimalIdAndProtectionStartDateAndProtectionFinishDate
                (
                        vaccineSaveRequest.getName(),
                        vaccineSaveRequest.getCode(),
                        vaccineSaveRequest.getAnimalId(),
                        vaccineSaveRequest.getProtectionStartDate(),
                        vaccineSaveRequest.getProtectionFinishDate()
                );

        if (checkVaccine.isPresent()) {
            throw new RuntimeException("Bu aşı zaten kayıtlı");
        }

        if (!vaccineSaveRequest.getProtectionFinishDate().isAfter(vaccineSaveRequest.getProtectionStartDate())) {
            throw new RuntimeException("Bitiş tarihi başlangıçtan ileri olamaz");
        }

        List<Vaccine> allVaccine = this.vaccineRepo.findByNameAndCodeAndAnimalId(vaccineSaveRequest.getName(), vaccineSaveRequest.getCode(), vaccineSaveRequest.getAnimalId());

        for (Vaccine vaccine : allVaccine) {
            if (!vaccineSaveRequest.getProtectionFinishDate().isAfter(vaccine.getProtectionStartDate())) { //vaccine.getProtectionStartDate().isAfter(vaccineSaveRequest.getProtectionFinishDate())
                throw new RuntimeException("Aşı Tarihi Dolmadı");
            }
            if (!vaccineSaveRequest.getProtectionStartDate().isAfter(vaccine.getProtectionFinishDate())) { //vaccine.getProtectionFinishDate().isAfter(vaccineSaveRequest.getProtectionFinishDate())
                throw new RuntimeException("Başlangıç tarihi diğer aşının bitiş tarihinden sonra olmalı");
            }
        }

        Vaccine saveVaccine = this.modelMapper.forRequest().map(vaccineSaveRequest, Vaccine.class);
        saveVaccine.setId(0);
        Animal animal = this.animalService.getOne(vaccineSaveRequest.getAnimalId());
        saveVaccine.setAnimal(animal);
        this.vaccineRepo.save(saveVaccine);
        return this.modelMapper.forResponse().map(saveVaccine, VaccineResponse.class);

    }

    @Override
    public VaccineResponse get(int id) {
        Vaccine vaccine = this.getOne(id);
        return this.modelMapper.forResponse().map(vaccine, VaccineResponse.class);
    }

    @Override
    public Page<VaccineResponse> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Vaccine> vaccinepage = this.vaccineRepo.findAll(pageable);
        return vaccinepage.map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class));
    }

    @Override
    public VaccineResponse update(VaccineUpdateRequest vaccineUpdateRequest) {
//        Optional<Vaccine> checkVaccine = vaccineRepo.findByNameAndCodeAndAnimalIdAndProtectionStartDateAndProtectionFinishDate
//                (
//                        vaccineUpdateRequest.getName(),
//                        vaccineUpdateRequest.getCode(),
//                        vaccineUpdateRequest.getAnimalId(),
//                        vaccineUpdateRequest.getProtectionStartDate(),
//                        vaccineUpdateRequest.getProtectionFinishDate()
//                );
//        if (checkVaccine.isPresent()) {
//            throw new AlreadyExistException("Veri Var");
//        }
//        if (!vaccineUpdateRequest.getProtectionFinishDate().isAfter(vaccineUpdateRequest.getProtectionStartDate())) {
//            throw new RuntimeException("Bitiş tarihi başlangıçtan ileri olamaz");
//        }
//
//        List<Vaccine> allVaccine = this.vaccineRepo.findByNameAndCodeAndAnimalId(vaccineUpdateRequest.getName(), vaccineUpdateRequest.getCode(), vaccineUpdateRequest.getAnimalId());
//
//        for (Vaccine vaccine : allVaccine) {
//            if (!vaccineUpdateRequest.getProtectionFinishDate().isAfter(vaccine.getProtectionStartDate())) { //vaccine.getProtectionStartDate().isAfter(vaccineSaveRequest.getProtectionFinishDate())
//                throw new RuntimeException("Aşı Tarihi Dolmadı");
//            }
//            if (!vaccineUpdateRequest.getProtectionStartDate().isAfter(vaccine.getProtectionFinishDate())) { //vaccine.getProtectionFinishDate().isAfter(vaccineSaveRequest.getProtectionFinishDate())
//                throw new RuntimeException("Başlangıç tarihi diğer aşının bitiş tarihinden sonra olmalı");
//            }
//        }

        Vaccine updateVaccine = this.modelMapper.forRequest().map(vaccineUpdateRequest, Vaccine.class);
        Vaccine vaccine = this.getOne(vaccineUpdateRequest.getId());
        Animal animal = this.animalService.getOne(vaccineUpdateRequest.getAnimalId());
        updateVaccine.setAnimal(animal);
        updateVaccine.setProtectionStartDate(vaccine.getProtectionStartDate());
        updateVaccine.setProtectionFinishDate(vaccine.getProtectionFinishDate());
        this.vaccineRepo.save(updateVaccine);
        return this.modelMapper.forResponse().map(updateVaccine, VaccineResponse.class);

    }

    @Override
    public boolean delete(int id) {
        Vaccine vaccine = this.getOne(id);
        this.vaccineRepo.delete(vaccine);
        return true;
    }
}
