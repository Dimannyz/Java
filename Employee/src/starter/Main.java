package starter;

import java.io.IOException;

import employee.EmployeeForm;
import employee.EmployeeFormListener;

public class Main {

    public static void main (String [] arg) throws IOException {

        EmployeeForm employeeForm = new EmployeeForm();
        EmployeeFormListener employeeFormListener = new EmployeeFormListener(employeeForm);
    }

}