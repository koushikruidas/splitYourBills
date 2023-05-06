/*
 * package com.projectY.splitYourBills.service;
 * 
 * import java.util.List; import java.util.Optional;
 * 
 * import org.modelmapper.ModelMapper;
 * 
 * import com.projectY.splitYourBills.entity.User; import
 * com.projectY.splitYourBills.exception.ResourceNotFoundException; import
 * com.projectY.splitYourBills.model.UserReqModel; import
 * com.projectY.splitYourBills.repo.UserRepository;
 * 
 * public class UserService implements IUserService {
 * 
 * private UserRepository userRepository; private ModelMapper mapper;
 * 
 * public UserService(UserRepository userRepository, ModelMapper mapper) {
 * super(); this.userRepository = userRepository; this.mapper = mapper; }
 * 
 * @Override public User add(UserReqModel userReq) { // TODO Auto-generated
 * method stub User user = mapper.map(userReq, User.class); return
 * userRepository.save(user); }
 * 
 * @Override public Optional<User> findById(long id) { // TODO Auto-generated
 * method stub
 * 
 * return userRepository.findById(id); }
 * 
 * @Override public List<User> findAll() { // TODO Auto-generated method stub
 * return userRepository.findAll(); }
 * 
 * @Override public void delete(long id) { // TODO Auto-generated method stub
 * Optional<User> user = userRepository.findById(id); if(user.isPresent()) {
 * userRepository.delete(user.get()); } }
 * 
 * @Override public User update(User userReq) { // TODO Auto-generated method
 * stub User existingUser = userRepository.findById(userReq.getUserId())
 * .orElseThrow(() -> new ResourceNotFoundException("User","Id",
 * userReq.getUserId())); existingUser.setUserName(userReq.getUserName());
 * return userRepository.save(existingUser); }
 * 
 * }
 */