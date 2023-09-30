package org.example.server.exception;

// 서버에서 나는 모든 예외를 감싸고자 생성
// 1. 예외 처리하기 편하도록 / 2. 애플리케이션 단의 예외와 그외의 예외를 따로 다루기 위해 생성
public class ServerException extends RuntimeException {
}