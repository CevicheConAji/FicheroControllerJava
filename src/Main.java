import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static String rutafichero = "/Users/piero/javatest";

    public static void main(String[] args) {

        //Users/piero/javatest

        test();


    }
    public static void test() {
        System.out.println("Introduzca la ruta en la que se creará la estructura");
        //definimos la ruta en la que se encuentra el fichero

        rutafichero = sc.nextLine();
        rutafichero += "/javatest";

        File ficheroMain = new File(rutafichero);
        if(ficheroMain.exists()) {
            imprimirRuta();
        }else{

        ficheroMain.mkdir();
        File ficheroD1 = new File(rutafichero+"/d1");
        ficheroD1.mkdir();
        File ficheroD2 = new File(rutafichero+"/d2");
        ficheroD2.mkdir();
        File textoD1 = new File(rutafichero+"/d1/f11.txt");
        File textD2 = new File(rutafichero+"/d2/f21.txt");
        try {
            textoD1.createNewFile();
            textD2.createNewFile();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Se a creado la extructura");

        }

    }

    public static void imprimirRuta() {
        System.out.println("IMPRIMIENDO RUTA");
        //Creamos un objeto fichero con esa ruta
        File fichero = new File(rutafichero);

        File[] ficheros = fichero.listFiles();

        //Verificacmos si el fichero existe
        if(fichero.exists()){
            //Recorremos y comprobamos si es fichero y si es un directorio
            for(File file : ficheros){

                if(fichero.isFile()){
                    System.out.println("\t-"+file.getName()+" es un fichero");
                }

                else if(fichero.isDirectory()){
                    System.out.println("*"+file.getName()+" es un directorio");

                    File[] subdirectorios = file.listFiles();
                    if(subdirectorios != null){
                        for(File subdir : subdirectorios){
                            System.out.println("\t-"+subdir.getName());
                        }
                    }else{
                        System.out.println("\t-El directorio esta vacio");
                    }

                }

            }
            modificarFicheros();
        }
        else System.out.println("El fichero no existe.");
    }
    public static void modificarFicheros(){
        System.out.println("MODIFICAR FICHEROS");
        String ficheroCambiar;
        String ficheroNuevo;
        System.out.print("***Inserte la ruta del archivo que desea cambiar el nombre*** " +
                "\nUsted esta en :"+rutafichero);
        ficheroCambiar = sc.nextLine();
        File fichero = new File(rutafichero+ficheroCambiar);
        if(fichero.exists()){
            System.out.print("***Introduzca la ruta y el nuevo nombre del archivo***. " +
                    "\nUsted esta en :"+rutafichero);
            ficheroNuevo = sc.nextLine();
            //Arreglar
            fichero.renameTo(new File(rutafichero+ficheroNuevo));
            System.out.println("Se cambio el nombre del fichero "+ficheroNuevo+" a "+ficheroNuevo);
            moverFicheroConFiles();
        }
    }
    public static void moverFicheroConFiles(){
        String ficheroMover;
        String directorioNuevo = "";
        String nuevoNombre ;

        System.out.println("MOVER FICHERO");
        System.out.print("Introduzca el fichero que quiere mover\nUsted esta en :"+rutafichero);
        ficheroMover = sc.nextLine();
        System.out.print("Introduzca la ruta a donde quieres mover el fichero \nUsted esta en :"+rutafichero);
        directorioNuevo = sc.nextLine();
        String[] s = ficheroMover.split("/");

        nuevoNombre = s[s.length-1];
        System.out.println("Se movio el fichero:"+nuevoNombre+" a "+directorioNuevo);

        try {
            Files.move(Paths.get(rutafichero+ficheroMover),
                    Paths.get(rutafichero+directorioNuevo).resolve(nuevoNombre),StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}