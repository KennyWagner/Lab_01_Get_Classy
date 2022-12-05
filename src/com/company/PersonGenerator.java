package com.company;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.*;

public class PersonGenerator
{

    public static void main(String[] args)
    {
        // Test data the lines of the file we will write
        ArrayList <Person> recs = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        Person p;
        boolean done = false;

        String ID;
        String firstName;
        String lastName;
        String title;
        int YOB;

        do
        {
            ID = SafeInput.getRegExString(s, "Enter your ID", "00000\\d");
            firstName = SafeInput.getNonZeroLengthString(s, "Enter your First Name");
            lastName = SafeInput.getNonZeroLengthString(s, "Enter your Last Name");
            title = SafeInput.getNonZeroLengthString(s, "Enter your Title");
            YOB = SafeInput.getRangedInt(s, "Enter your Year of Birth", 1940, 2000);

            p = new Person(ID, firstName, lastName, title, YOB);
            recs.add(p);

            done = SafeInput.getYNConfirm(s, "Are you done? ");
        } while(!done);



        // uses a fixed known path:
        //  Path file = Paths.get("c:\\My Documents\\data.txt");

        // use the toolkit to get the current working directory of the IDE
        // will create the file within the Netbeans project src folder
        // (may need to adjust for other IDE)
        // Not sure if the toolkit is thread safe...
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\PersonTestData.txt");

        try
        {
            // Typical java pattern of inherited classes
            // we wrap a BufferedWriter around a lower level BufferedOutputStream
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            // Finally can write the file LOL!
            String r;
            for(Person rec : recs)
            {
                r = rec.toCSVDataRecord();
                System.out.println("Record " + r);
                writer.write(r, 0, r.length());  // stupid syntax for write rec
                // 0 is where to start (1st char) the write
                // rec. length() is how many chars to write (all)
                writer.newLine();  // adds the new line

            }
            writer.close(); // must close the file to seal it and flush buffer
            System.out.println("Data file written!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}