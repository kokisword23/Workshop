package softuni.workshop.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import softuni.workshop.domain.models.binding.UserRegisterBindingModel;
import softuni.workshop.domain.models.service.UserServiceModel;

public interface UserService extends UserDetailsService {

    UserServiceModel registerUser(UserServiceModel userServiceModel);

    UserServiceModel findUserByUserName(String username);
}
