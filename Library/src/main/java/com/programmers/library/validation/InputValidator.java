package com.programmers.library.validation;

import com.programmers.library.exception.ErrorCode;
import com.programmers.library.exception.ExceptionHandler;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputValidator {

    public static Long checkNumberValidate(Scanner scanner){
        try{
            return Long.valueOf(scanner.nextLine());
        }catch (InputMismatchException e){
            throw ExceptionHandler.err(ErrorCode.INVALID_INPUT_EXCEPTION);
        }catch (NoSuchElementException e){
            throw ExceptionHandler.err(ErrorCode.NO_INPUT_EXCEPTION);
        }
    }
}
