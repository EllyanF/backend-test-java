package com.fcamara.backendtest.service;

import com.fcamara.backendtest.domain.Vehicle;
import com.fcamara.backendtest.dto.vehicle.UpdateVehicleDTO;
import com.fcamara.backendtest.dto.vehicle.VehicleDTO;
import com.fcamara.backendtest.repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper;

    public VehicleService(VehicleRepository vehicleRepository, ModelMapper modelMapper) {
        this.vehicleRepository = vehicleRepository;
        this.modelMapper = modelMapper;
    }

    public Vehicle create(VehicleDTO data) {
        Vehicle vehicle = new Vehicle(data);

        return vehicleRepository.save(vehicle);
    }

    public Page<Vehicle> getAll(Pageable pageable) {
        return vehicleRepository.findAll(pageable);
    }

    public Vehicle getById(Long id) {
        return vehicleRepository.findById(id).orElseThrow();
    }

    public Vehicle updateById(Long id, UpdateVehicleDTO data) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow();
        modelMapper.map(data, vehicle);

        return vehicleRepository.save(vehicle);
    }

    public void deleteById(Long id) {
        vehicleRepository.deleteById(id);
    }
}
