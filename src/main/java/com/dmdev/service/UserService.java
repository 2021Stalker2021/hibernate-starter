package com.dmdev.service;

import com.dmdev.dao.UserDao;
import com.dmdev.dao.UserRepository;
import com.dmdev.dto.UserCreateDto;
import com.dmdev.dto.UserReadDto;
import com.dmdev.entity.User;
import com.dmdev.mapper.Mapper;
import com.dmdev.mapper.UserCreateMapper;
import com.dmdev.mapper.UserReadMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserCreateMapper userCreateMapper;
    private final UserReadMapper userReadMapper;

    @Transactional // открываем транзакцию
    public Long create(UserCreateDto userDto) {
        // validation
        var userEntity = userCreateMapper.mapFrom(userDto);
        return userRepository.save(userEntity).getId();
    }

    @Transactional
    public Optional<UserReadDto> findById(Long id) {
        return findById(id, userReadMapper);
    }

    @Transactional
    public <T> Optional<T> findById(Long id, Mapper<User, T> mapper) {
        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJakartaHintName(), userRepository.getEntityManager().getEntityGraph("WithCompany")
        );
        return userRepository.findById(id, properties)
                .map(mapper::mapFrom);
    }

    @Transactional
    public boolean delete(Long id) {
        var maybeUser = userRepository.findById(id);
        maybeUser.ifPresent(user -> userRepository.delete(user.getId()));
        return maybeUser.isPresent();
    }
}
