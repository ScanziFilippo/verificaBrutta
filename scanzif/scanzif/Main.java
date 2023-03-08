package scanzif;
import java.time.*;

public class Main
{
    
    public Main() throws scanzif.NotUniqueOperatorException
    {
        CallCenter si = new CallCenter();
        System.out.println(si.registraOperatore("Filippo", "Scanzi", LocalDate.of(2005,4,16)));
        System.out.println(si.operatori.get("FilippoScanzi2005-04-16"));
        try
        {
            System.out.println(si.registraValutazione("FilippoScanzi2005-04-16", 2, LocalDate.of(2023,3,7)));
        }
        catch (scanzif.InvalidOperatorException ioe)
        {
            ioe.printStackTrace();
        }
        System.out.println(si.operatori.get("FilippoScanzi2005-04-16"));
        System.out.println(si.toString());
        System.out.println(si.valutazioneComplessiva("FilippoScanzi2005-04-16"));
    }
}
