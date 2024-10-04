import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //definimos la ruta en la que se encuentra el fichero
        String rutafichero = "";
        System.out.println("Escribe la ruta que quieres listar:");
        rutafichero = sc.nextLine();

        creandoFileStructure(rutafichero);
        existeDirectorioImprimeRuta(rutafichero);

    }
    public static void creandoFileStructure(String ruta) {

        File ficheroMain = new File(ruta);
        if(ficheroMain.exists()) {
            System.out.println("El archivo ya existe");
        }else{

        ficheroMain.mkdir();
        File ficheroD1 = new File(ruta+"d1");
        ficheroD1.mkdir();
        File ficheroD2 = new File(ruta+"d2");
        ficheroD2.mkdir();
        File textoD1 = new File(ruta+"d1/f11.txt");
        File textD2 = new File(ruta+"d2/f12.txt");
        try {
            textoD1.createNewFile();
            textD2.createNewFile();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Se a creado la extructura");

        }

    }

    public static void existeDirectorioImprimeRuta(String ruta) {
        //Creamos un objeto fichero con esa ruta
        File fichero = new File(ruta);

        File[] ficheros = fichero.listFiles();

        //Verificacmos si el fichero existe
        if(fichero.exists()){
            //Recorremos y comprobamos si es fichero y si es un directorio
            for(File file : ficheros){

                if(fichero.isFile()){
                    System.out.println("\t-"+file.getName()+" es un fichero");
                }
                if(fichero.isDirectory()){
                    System.out.println("*"+file.getName()+" es un directorio");
                    System.out.println(Arrays.toString(file.list()));
                }

            }
        }
        else System.out.println("El fichero no existe.");
    }
}