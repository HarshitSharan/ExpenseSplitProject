package org.hsharan.userAuth;


public class UserValidations {


    public static void validateUserPhoneNum(String phoneNum) throws ValidationException{
        if(!phoneNum.matches("^\\d{10}$")){
            throw new ValidationException("Enter Valid Phone Number");
        }
    }
    public static void validateUserName(String userName) throws ValidationException{
        if(!userName.matches("^[a-zA-Z\\s]+$")){
            throw new ValidationException("Enter valid Name");
        }
    }
    public static void validateUserEmail(String email) throws ValidationException{
        if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            throw new ValidationException("Enter valid Email");
        }
    }
    public static void validateUser(UserDetails userDetails) throws ValidationException{
        validateUserName(userDetails.getName());
        validateUserEmail(userDetails.getEmail());
        validateUserPhoneNum(userDetails.getPhoneNum());


    }
}
