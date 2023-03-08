package scanzif;
import java.time.LocalDate;
import java.util.HashMap;

public class Operator
{
    String name;
    String surname;
    LocalDate birthDate;
    String matricola;
    HashMap<Integer, Feedback> feedback = new HashMap<Integer, Feedback>();
    public Operator(String name, String surname, LocalDate birthDate, String matricola)
    {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.matricola = matricola;
    }
    public String getMatricola(){
        return name + surname + birthDate;
    }
    public String toString(){
        return name + " " + surname + " " + birthDate + " " + matricola + " " + feedback.toString();
    }
}
