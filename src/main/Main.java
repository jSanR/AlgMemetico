package main;

import algoritmo.AlgoritmoMemetico;
import datos_param.DatosEntrada;
import logging.MultiOutputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args){
        //Configuración para que la salida y error estándar se copien a un archivo
        try {
            configurarSalida();
        }catch (FileNotFoundException ex){
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        //Path en donde se ubica el archivo de datos. Se recomienda encarecidamente ingresar el path como argumento
        //del programa. Sin embargo, en caso de no hacerse, el programa intentará leer un archivo llamado "datos.csv"
        //que se ubique en el mismo directorio que el .jar
        String pathArchivoDatos;
        if(args.length == 1) pathArchivoDatos = args[0];
        else pathArchivoDatos = null;
        //Lectura de datos de entrada desde el archivo en cuestión
        DatosEntrada datos = DatosEntrada.getInstance(pathArchivoDatos);
        //System.out.println(datos);
        //Ejecución de algoritmo
        AlgoritmoMemetico algMem = new AlgoritmoMemetico();
        algMem.ejecutar(datos.getVerbosityLevel());
    }

    public static void configurarSalida() throws FileNotFoundException{
        ZonedDateTime horaEjecucion = ZonedDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        //Obtenido de: https://www.codeproject.com/Tips/315892/A-quick-and-easy-way-to-direct-Java-System-out-to
        FileOutputStream fout = new FileOutputStream("out-" + horaEjecucion.format(formato) + ".txt");
        FileOutputStream ferr = new FileOutputStream("err-" + horaEjecucion.format(formato) + ".txt");

        MultiOutputStream multiOut = new MultiOutputStream(System.out, fout);
        MultiOutputStream multiErr = new MultiOutputStream(System.err, ferr);

        PrintStream stdout = new PrintStream(multiOut);
        PrintStream stderr = new PrintStream(multiErr);

        System.setOut(stdout);
        System.setErr(stderr);
    }
}
