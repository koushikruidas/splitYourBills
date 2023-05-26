
package com.projectY.splitYourBills.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.projectY.splitYourBills.entity.User;
import com.projectY.splitYourBills.exception.ResourceNotFoundException;
import com.projectY.splitYourBills.model.UserDTO;
import com.projectY.splitYourBills.repo.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private ModelMapper mapper;

	public UserServiceImpl (UserRepository userRepository, ModelMapper mapper) {
		this.userRepository = userRepository;
		this.mapper = mapper;
	}
	
	@Override
	public List<UserDTO> findAll() { 
		List<User> users = userRepository.findAll();
		return (
				users.stream()
				.map(user -> mapper.map(user, UserDTO.class))
				.collect(Collectors.toList())
				);
	}

	@Override
	public UserDTO createUser(UserDTO userDto) {
		User user = mapper.map(userDto, User.class);
		userRepository.save(user);
		return mapper.map(user, UserDTO.class);
	}

	@Override
	public UserDTO findById(long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not Found"));
		return mapper.map(user, UserDTO.class);
	}


	@Override
	public void delete(long id) {
		userRepository.deleteById(id);
	}

	@Override
	public UserDTO update(UserDTO userDto, long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("user not found"));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPhone(userDto.getPhone());
		userRepository.save(user);
		return mapper.map(user, UserDTO.class);
	}
}
