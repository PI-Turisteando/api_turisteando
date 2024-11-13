package com.proyecto.turisteando.services.implement;

import com.proyecto.turisteando.dtos.IDto;
import com.proyecto.turisteando.dtos.requestDto.CategoryRequestDto;
import com.proyecto.turisteando.dtos.requestDto.CharacteristicRequestDto;

import com.proyecto.turisteando.entities.CategoryEntity;
import com.proyecto.turisteando.entities.CharacteristicEntity;
import com.proyecto.turisteando.exceptions.customExceptions.CharacteristicNotFoundException;
import com.proyecto.turisteando.mappers.CharacteristicMapper;
import com.proyecto.turisteando.repositories.CharacteristicRepository;
import com.proyecto.turisteando.services.ICharacteristicService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Slf4j
@RequiredArgsConstructor
@Service
public class CharacteristicServiceImpl implements ICharacteristicService {

    private final CharacteristicRepository characteristicRepository;
    private final CharacteristicMapper characteristicMapper;

    @Override
    public Iterable<IDto> getAll() {
        //        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        boolean isAdmin = auth.getAuthorities().stream()
//                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
//
//        if (isAdmin){
//            return characteristicRepository.findAll().stream()
//                    .map(characteristicMapper::toDto)
//                    .toList();
//        }

        Iterable<CharacteristicEntity> characteristic = characteristicRepository.findByStatus((byte) 1);

        return StreamSupport.stream(characteristic.spliterator(), false)
                .map(characteristicMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public IDto read(Long id) {
        //        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        boolean isAdmin = auth.getAuthorities().stream()
//                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
//
//        CharacteristicsEntity characteristicEntity;
//
//        if (!isAdmin){
//            characteristicEntity = characteristicRepository.findByIdAndStatus(id, 1)
//                    .orElseThrow(() -> new CharacteristicNotFoundException("No se encontró la característica"));
//        }else{
//            characteristicEntity = characteristicRepository.findById(id)
//                    .orElseThrow(() -> new CharacteristicNotFoundException("No se encontró la característica"));
//        }
//
//        return characteristicMapper.toDto(characteristicEntity);
        return characteristicMapper.toDto(characteristicRepository.findById(id).orElse(null));
//        return null;
    }

    @Override
    public IDto create(IDto dto) {
        CharacteristicRequestDto characteristicDto = (CharacteristicRequestDto) dto;

        try {
            CharacteristicEntity characteristicEntity = characteristicRepository.save(characteristicMapper.toEntity(characteristicDto));
            return characteristicMapper.toDto(characteristicEntity);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("name")) {
                throw new ServiceException("Ya existe la Característica", e);
            }
            throw new ServiceException("Error al crear la Característica", e);
        } catch (Exception e) {
            throw new ServiceException("Error al crear la Característica: ", e);
        }
    }

    @Override
    public IDto update(IDto dto, Long id) {
        CharacteristicRequestDto characteristicDto = (CharacteristicRequestDto) dto;

        CharacteristicEntity characteristic =  characteristicRepository.findById(id)
                .orElseThrow(() -> new CharacteristicNotFoundException("No se encontró la característica"));

        characteristicMapper.partialUpdate(characteristicDto, characteristic);
        CharacteristicEntity updatedCharacteristic =  characteristicRepository.save(characteristic);

        return  characteristicMapper.toDto(updatedCharacteristic);
    }




    @Override
    public IDto delete(Long id) {
        CharacteristicEntity characteristicEntity = characteristicRepository.findByIdAndStatus(id, 1)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la característica a eliminar"));

        characteristicEntity.setStatus((byte) 0);
        characteristicRepository.save(characteristicEntity);

        return characteristicMapper.toDto(characteristicEntity);
    }

    @Override
    public IDto toggleStatus(Long id) {
        CharacteristicEntity characteristicEntity  = characteristicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la característica"));

        byte newStatus = (characteristicEntity.getStatus() == 1) ? (byte) 0 : (byte) 1;
        characteristicEntity.setStatus(newStatus);

        characteristicRepository.save(characteristicEntity);
        return characteristicMapper.toDto(characteristicEntity);
    }
}