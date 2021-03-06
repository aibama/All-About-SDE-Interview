package org.gnuhpc.interview.designpattern.memento.caretaker;

import org.gnuhpc.interview.designpattern.memento.originator.Employee;
import org.gnuhpc.interview.designpattern.memento.memento.EmployeeMemento;

import java.util.Stack;

//caretaker
public class Caretaker {

    private Stack<EmployeeMemento> employeeHistory = new Stack<>();

    public void save(Employee emp) {
        employeeHistory.push(emp.save());
    }

    public void revert(Employee emp) {
        emp.revert(employeeHistory.pop());
    }
}
