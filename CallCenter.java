package scanzif;
import java.time.LocalDate;
import java.util.*;

public class CallCenter
{
    HashMap<String, Operator> operatori = new HashMap<String, Operator>();
    int somma;
    public CallCenter()
    {
        
    }
    
    public String registraOperatore(String n, String c, LocalDate b) throws NotUniqueOperatorException {
        String matricola = n + c + b;
        if(operatori.containsKey(matricola)){
            throw new NotUniqueOperatorException();
        }else{
            operatori.put(matricola, new Operator(n, c, b, matricola));
            return matricola;
        }
    }
    
    public int registraValutazione(String m, int s, LocalDate d) throws scanzif.InvalidOperatorException {
        int id = Integer.parseInt((s + "" + d.getYear() + "" + d.getMonthValue() + "" + d.getDayOfMonth()));
        if(operatori.containsKey(m)){
            operatori.get(m).feedback.put(id, new Feedback(m, s,d, id));
            return id;
        }else{
            throw new InvalidOperatorException();
        }
    }
    
    public String toString(){
        return operatori.toString();
    }
    
    /*public Feedback[] restituisciValutazioni(){
        int somma = 0;
        operatori.forEach((key, value) -> somma += value.);
    }*/
    
    public int valutazioneComplessiva(String matricola){
        HashMap<Integer, Feedback> a = operatori.get(matricola).feedback;
        somma = 0;
        a.forEach((key, value) -> somma += value.punteggio);
        return somma;
    }
}
class NotUniqueOperatorException extends Exception{}
class InvalidOperatorException extends Exception{}