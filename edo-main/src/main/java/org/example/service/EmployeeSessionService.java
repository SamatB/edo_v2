package org.example.service;

public interface EmployeeSessionService {

    void saveEmployeeSession(String sessionId, String username);

    void deleteEmployeeSession(String sessionId);

    int getOnlineUsersNumber();
}
