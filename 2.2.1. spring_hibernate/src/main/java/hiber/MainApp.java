package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      User user1 = new User("Vanya", "Mochov", "moch@gmail.com");
      User user2 = new User("Albina", "Ronina", "ronchi@gmail.com");
      User user3 = new User("Masha", "Novikova", "mashanov@gmail.com");
      User user4 = new User("Misha", "Rudich", "micha@gmail.com");

      Car car1 = new Car("Volvo", 5);
      Car car2 = new Car("Lada", 1351);
      Car car3 = new Car("BMW", 534);
      Car car4 = new Car("Folswagen", 7);

      userService.add(user1.setCar(car1).setUser(user1));
      userService.add(user2.setCar(car2).setUser(user2));
      userService.add(user3.setCar(car3).setUser(user3));
      userService.add(user4.setCar(car4).setUser(user4));

      // пользователи с машинами
      for (User user : userService.listUsers()) {
         System.out.println(user + " " + user.getCar());
      }

      // достать юзера, владеющего машиной по ее модели и серии
      System.out.println(userService.getUserByCar("Volvo", 5));

      // нет такого юзера с такой машиной
      try {
         User notFoundUser = userService.getUserByCar("GAZ", 4211);
      } catch (NoResultException e) {
         System.out.println("User not found");
      }

      context.close();
   }
}
