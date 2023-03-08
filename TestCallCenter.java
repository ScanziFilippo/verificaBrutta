package mastroiannim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestCallCenter {

    static final int MAX_VALUTAZIONI = 10;

    private String getRandomString(){
        return  "" + (int)(Math.random()*999999);
    }

    private int getRandomPunteggio(){
        return -3 + (int)(Math.random()*7);
    }

    private LocalDate getRandomData(){
        LocalDate ld =  LocalDate.now();
        return ld.minusDays((long) (Math.random()*365));
    }

    @Test
    public void testToStringCallCenter(){
        CallCenter c1 = new CallCenter();
        CallCenter c2 = new CallCenter();
        assertEquals(c1.toString(), c2.toString());
    }

    @Test
    public void testRegistraOperatore(){
        CallCenter c = new CallCenter();
        LocalDate bd = getRandomData();
        try {
            c.registraOperatore("M", "M", bd);
        } catch (NotUniqueOperatorException e) {
            fail("NotUniqueOperatorException");
        }
    }

    @Test
    public void testRegistraOperatoreNotUnique(){
        assertThrows(NotUniqueOperatorException.class, () -> {
            CallCenter c = new CallCenter();
            LocalDate bd = getRandomData();
            c.registraOperatore("M", "M", bd);
            c.registraOperatore("M", "M", bd);
        });  
    }

    @Test
    public void testRegistraValutazione(){
        CallCenter c = new CallCenter();
        int punteggio = getRandomPunteggio();
        LocalDate dataValutazione = getRandomData();
        LocalDate bd = getRandomData();
        try {
            String matricola =  c.registraOperatore("M", "M", bd);
            c.registraValutazione(matricola, punteggio, dataValutazione);
        } catch (InvalidOperatorException e) {
            fail("InvalidOperatorException");
        } catch (NotUniqueOperatorException e) {
            fail("NotUniqueOperatorException");
        }
    }

    @Test
    public void testRegistraValutazioneInvalidOperator(){
        assertThrows(InvalidOperatorException.class, () -> {
            CallCenter c = new CallCenter();
            int punteggio = getRandomPunteggio();
            LocalDate dataValutazione = getRandomData();
            c.registraValutazione(getRandomString(), punteggio, dataValutazione);
        });  
    }

    
    @Test
    public void testRestituisciValutazioni(){
        CallCenter c = new CallCenter();
        String matricola;
        int punteggio = getRandomPunteggio();
        LocalDate data = getRandomData();
        int id1 = -1;
        int id2 = -1;
        try {
            matricola =  c.registraOperatore(getRandomString(), getRandomString(), getRandomData());
            id1 = c.registraValutazione(matricola, punteggio, data);
            id2 = c.registraValutazione(matricola, punteggio, data);    
        } catch (InvalidOperatorException e) {
            fail();
        } catch (NotUniqueOperatorException e) {
            fail();
        }
        
        Feedback[] valutazioni = c.restituisciValutazioni();
        assertEquals(2, valutazioni.length);
        assertNotEquals(valutazioni[0].toString(), valutazioni[1].toString());
        assertEquals((id1+1), id2);
    }

    @Test
    public void testValutazioneComplessiva(){
        CallCenter c = new CallCenter();
        String matricola;
        LocalDate data = getRandomData();
        int tot = Integer.MIN_VALUE;
        try {
            String michele = c.registraOperatore("michele", getRandomString(), getRandomData());
            matricola =  c.registraOperatore(getRandomString(), getRandomString(), getRandomData());
            c.registraValutazione(matricola, -3, data);
            c.registraValutazione(matricola, -3, data); 
            c.registraValutazione(matricola, 1, data); 
            c.registraValutazione(michele, -3, data); 
            tot = c.valutazioneComplessiva(matricola);
        } catch (InvalidOperatorException e) {
            fail();
        } catch (NotUniqueOperatorException e) {
            fail();
        }
        assertEquals(-5, tot);
    }

    @Test
    public void testValutazioniNegative(){
        CallCenter c = new CallCenter();
        String op1 = null, op2 = null, op3 = null, op4= null;
        try {
            //tot op1 = -1
            op1 = c.registraOperatore("op1", getRandomString(), getRandomData());
            //tot op2 = 0
            op2 = c.registraOperatore("op2", getRandomString(), getRandomData());
            //tot op3 = 1
            op3 = c.registraOperatore("op3", getRandomString(), getRandomData());
            //tot op4 = -1
            op4 = c.registraOperatore("op4", getRandomString(), getRandomData());
            c.registraValutazione(op1, -3, getRandomData());
            c.registraValutazione(op1, 0, getRandomData());
            c.registraValutazione(op1, 1, getRandomData());
            c.registraValutazione(op2, -1, getRandomData());
            c.registraValutazione(op2, -2, getRandomData());
            c.registraValutazione(op2, 3, getRandomData());
            c.registraValutazione(op3, 1, getRandomData());
            c.registraValutazione(op4, -1, getRandomData());

        } catch (InvalidOperatorException e) {
            fail();
        } catch (NotUniqueOperatorException e) {
            fail();
        }
        Operator[] operatori = c.valutazioniNegative();
        assertEquals(2, operatori.length);

        List<Operator> lista = Arrays.asList(operatori);
        Operator uno = null, quattro = null;
        for ( Operator i : lista){
            //System.out.println(i);
            if(i.getMatricola().equals(op1))
                uno = i;
            if(i.getMatricola().equals(op4))
                quattro = i;
        }
        assertNotNull(uno);
        assertNotNull(quattro);
    }

    @Test
    public void testValutazioneComplessivaMese(){
    
        int mese = 1;
        int anno = 2021;
        CallCenter c = new CallCenter();
        String matricola;
        LocalDate data1 = LocalDate.of(2021, 1, 28);
        LocalDate data2 = LocalDate.of(2021, 1, 1);
        LocalDate data3 = LocalDate.of(2022, 1, 30);

        int tot = Integer.MIN_VALUE;
        try {
            String michele = c.registraOperatore("michele", "m", getRandomData());
            matricola =  c.registraOperatore(getRandomString(), getRandomString(), getRandomData());
            c.registraValutazione(matricola, -3, data1);
            c.registraValutazione(matricola, -3, data2); 
            c.registraValutazione(matricola, 1, data3); 
            c.registraValutazione(michele, -3, data1); 
            tot = c.valutazioneMese(matricola, mese, anno);

        } catch (InvalidOperatorException e) {
            fail();
        } catch (NotUniqueOperatorException e) {
            fail();
        }
        assertEquals(-6, tot);
    }

    @Test
    public void testBest(){
        CallCenter c = new CallCenter();
        String op1 = null, op2 = null, op3 = null, op4= null;
        try {
            //tot op1 = -1
            op1 = c.registraOperatore("op1", getRandomString(), getRandomData());
            //tot op2 = 1
            op2 = c.registraOperatore("op2", getRandomString(), getRandomData());
            //tot op3 = 1
            op3 = c.registraOperatore("op3", getRandomString(), getRandomData());
            //tot op4 = -1
            op4 = c.registraOperatore("op4", getRandomString(), getRandomData());
            c.registraValutazione(op1, -3, getRandomData());
            c.registraValutazione(op1, 0, getRandomData());
            c.registraValutazione(op1, 1, getRandomData());
            c.registraValutazione(op2, -1, getRandomData());
            c.registraValutazione(op2, -1, getRandomData());
            c.registraValutazione(op2, 3, getRandomData());
            c.registraValutazione(op3, 1, getRandomData());
            c.registraValutazione(op4, -1, getRandomData());

            Operator[] allBest = c.best();
            assertEquals(2, allBest.length);

            List<Operator> lista = Arrays.asList(allBest);
            Operator due = null, tre = null;
            for ( Operator i : lista){
                //System.out.println(i);
                if(i.getMatricola().equals(op2))
                    due = i;
                if(i.getMatricola().equals(op3))
                    tre = i;
            }
            assertNotNull(due);
            assertNotNull(tre);

            assertEquals(op2, due.getMatricola());
            assertEquals(op3, tre.getMatricola());

        } catch (InvalidOperatorException e) {
            fail();
        } catch (NotUniqueOperatorException e) {
            fail();
        }
    }

}
