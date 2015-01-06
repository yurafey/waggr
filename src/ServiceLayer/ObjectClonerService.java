package ServiceLayer;

/**
 * Created by yuraf_000 on 21.12.2014.
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class ObjectClonerService
{
    private ObjectClonerService(){}

    static public Object deepCopy(Object oldObj) throws Exception
    {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(oldObj);
            oos.flush();
            ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bin);
            return ois.readObject();
        }
        catch(Exception e)
        {
            System.out.println("Exception in ObjectCloner " + e);
            throw(e);
        }
        finally
        {
            oos.close();
            ois.close();
        }
    }

}