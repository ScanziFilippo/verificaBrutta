package scanzif;
import java.time.LocalDate;

public class Feedback
{
    String matricola;
    int punteggio;
    LocalDate data;
    int id;
    public Feedback(String matricola, int punteggio, LocalDate data, int id)
    {
        this.matricola = matricola;
        this.punteggio = punteggio;
        this.data = data;
        this.id = id;
    }
    
    public String toString(){
        return matricola + " " + punteggio + " " + data + " " + id;
    }
}
