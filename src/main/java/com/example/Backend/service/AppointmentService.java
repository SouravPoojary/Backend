package com.example.Backend.service;

import com.example.Backend.dto.AppointmentDto;
import com.example.Backend.entity.Appointment;
import com.example.Backend.entity.JobDetails;
import com.example.Backend.entity.Services;
import com.example.Backend.entity.User;
import com.example.Backend.exception.UserNotFoundException;
import com.example.Backend.repository.AppointmentRepository;
import com.example.Backend.repository.ServicesRepository;
import com.example.Backend.repository.UserRepository;
import com.example.Backend.resource.Role;
import com.example.Backend.resource.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private ServicesRepository servicesRepository;
    @Autowired
    private UserRepository userRepository;

    public Appointment dtoToEntity(AppointmentDto dto) {
        Appointment entity = new Appointment();
        entity.setId(dto.getId());
        Services service=servicesRepository.findById(dto.getServiceId().getId())
                .orElseThrow(()->new RuntimeException("Service not found with id:"+dto.getServiceId().getId()));
        User user=userRepository.findById(dto.getCustomerId().getId())
                .orElseThrow(()->new RuntimeException("UserId not found"));
        entity.setCustomerId(user);
        entity.setServiceId(service);
        entity.setVehicleName(dto.getVehicleName());
        entity.setRegNo(dto.getRegNo());
        entity.setDescription(dto.getDescription());
        entity.setAppointmentDate(dto.getAppointmentDate());
        entity.setAppointmentTime(dto.getAppointmentTime());
        entity.setStatus(dto.getStatus());
//        entity.setJobDetails(dto.getJobDetails());
        return entity;
    }

    public AppointmentDto entityToDto(Appointment entity) {
        AppointmentDto dto = new AppointmentDto();
        dto.setId(entity.getId());

        dto.setServiceId(entity.getServiceId());
        dto.setCustomerId(entity.getCustomerId());
        dto.setServiceId(entity.getServiceId());
        dto.setVehicleName(entity.getVehicleName());
        dto.setRegNo(entity.getRegNo());
        dto.setDescription(entity.getDescription());
        dto.setAppointmentDate(entity.getAppointmentDate());
        dto.setAppointmentTime(entity.getAppointmentTime());
        dto.setStatus(entity.getStatus());
        dto.setJobDetails(entity.getJobDetails());
        return dto;
    }

    public AppointmentDto create(AppointmentDto dto) {
//        Appointment entity = appointmentRepository.save(dtoToEntity(dto));
        Appointment entity = dtoToEntity(dto);
        Appointment savedEntity = appointmentRepository.save(entity);
        return entityToDto(savedEntity);
    }

    public List<AppointmentDto> getAll() {
        List<AppointmentDto> dtos = new ArrayList<>();
        for (Appointment entity : appointmentRepository.findAll()) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

    public AppointmentDto getById(Long id) throws UserNotFoundException {
        Appointment entity = appointmentRepository.findById(id).orElse(null);
        if (entity != null)
            return entityToDto(entity);
        else
            throw new UserNotFoundException("Appointment not found");
    }

    public void deleteById(Long id, Long userId, Role role) throws UserNotFoundException {
        Appointment entity = appointmentRepository.findById(id).orElseThrow(()->new UserNotFoundException("Appointment not found"));

        boolean isCustomer = entity.getCustomerId().getId().equals(userId);
        boolean isServiceCenter = entity.getServiceId() != null
                && entity.getServiceId().getServiceCenterId().equals(userId);

        if (role!=Role.ADMIN && !isCustomer && !isServiceCenter) {
            throw new UserNotFoundException("You can only delete your own appointments!");
        }
            appointmentRepository.deleteById(id);

    }

    public Appointment updateStatus(Long id, Status newStatus) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointment.setStatus(newStatus);
            return appointmentRepository.save(appointment);
        }
        throw new RuntimeException("Appointment not found");
    }

    public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
        return appointmentRepository.findById(id)
                .map(appointment -> {
                    appointment.setJobDetails(updatedAppointment.getJobDetails());
                    return appointmentRepository.save(appointment);
                })
                .orElseThrow(() -> new RuntimeException("Appointment not found with id " + id));
    }
//public Appointment addJobToAppointment(Long appointmentId, JobDetails jobDetails) {
//    return appointmentRepository.findById(appointmentId)
//            .map(appointment -> {
//                appointment.setJobDetails(jobDetails);
//                Appointment updatedAppointment = appointmentRepository.save(appointment);
//                System.out.println("Updated Appointment: " + updatedAppointment); // Debugging
//                return updatedAppointment;
//            })
//            .orElseThrow(() -> new RuntimeException("Appointment not found with ID " + appointmentId));
//}
}
