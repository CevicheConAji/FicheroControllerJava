import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static String rutafichero = "/home/pierozavala/javatest";
    public static String rutaMain = rutafichero;
    public static String op = " ";

    public static void main(String[] args) {

        //Users/piero/javatest

        menu();

    }
    public static void menu(){
        System.out.println("Bienvenido al sistema");

        while (!op.equalsIgnoreCase("s")) {
            System.out.println("***TEST***" +
                    "\n1)TEST01" +
                    "\n2)TEST02" +
                    "\nl)LISTAR" +
                    "\nb)BORRAR" +
                    "\nm)MODIFICAR FICHERO" +
                    "\nmo)MODIFICAR RUTA" +
                    "\ns)SALIR");
            op = sc.nextLine();
            switch (op) {
                case "1":test1();break;
                case "b":borrarFichero();break;
                case "l":imprimirDirectorio();break;
                case "m":modificarFicheros();break;
                case "i":break;
                case "o":break;
                case "s":break;
            }
        }
    }
    public static void test1() {
        System.out.println("Introduzca la ruta en la que se creará la estructura");
        //definimos la ruta en la que se encuentra el fichero

        /**
         * Activar antes de usar
         */
        //rutafichero = sc.nextLine();
        //String s [] = rutafichero.split("/");
        //rutaMain = ("/"+s[1]);
        //System.out.println(rutaMain);
        //rutafichero += "/javatest";

        File ficheroMain = new File(rutafichero);
        if(ficheroMain.exists()) {
            imprimirDirectorio();
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

    public static void imprimirDirectorio() {
        preguntaRuta();
        System.out.println("IMPRIMIENDO RUTA");

        //Creamos un objeto fichero con esa ruta
        File fichero = new File(rutaMain);

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
            if(op.equalsIgnoreCase("1")) modificarFicheros();

        }
        else System.out.println("El fichero no existe.");
    }
    public static void modificarFicheros(){
        String ficheroCambiar,ficheroNuevo;
        preguntaRuta();

        System.out.println("CAMBIAR NOMBRE FICHEROS O MOVER ENTRE DIRECTORIOS");
        System.out.print("***Inserte la ruta del archivo que desea cambiar el nombre*** " +
                "\nUsted esta en :"+rutafichero);
        ficheroCambiar = sc.nextLine();
        File fichero = new File(rutafichero+ficheroCambiar);
        if(fichero.exists()){
            System.out.print("***Introduzca la ruta y el nuevo nombre del archivo***. " +
                    "\nUsted esta en :"+rutafichero);
            ficheroNuevo = sc.nextLine();
            File nuevaRuta = new File(rutafichero+ficheroNuevo);
            
            fichero.renameTo(nuevaRuta);
            System.out.println("Se cambio el nombre del fichero "+ficheroCambiar+" a "+ficheroNuevo);

            //Entrar a test si esta en modo test
            if(op.equalsIgnoreCase("t")){
                moverFicheroConFilesRename();

            }
        }
        else System.out.println("El fichero no existe.");
    }
    public static void moverFicheroConFilesRename(){
        String ficheroMover,directorioNuevo,nuevoNombre ;

        preguntaRuta();

        System.out.println("MOVER FICHERO");
        System.out.print("Introduzca el fichero que quiere mover\nUsted esta en :"+rutafichero);
        ficheroMover = sc.nextLine();
        System.out.print("Introduzca la ruta a donde quieres mover el fichero \nUsted esta en :"+rutafichero);
        directorioNuevo = sc.nextLine();
        String[] s = ficheroMover.split("/");

        nuevoNombre = s[s.length-1];
        System.out.println("Se movio el fichero:"+nuevoNombre+" a "+directorioNuevo);
        //agregar cuandi esta vacio
        try {
            Files.move(Paths.get(rutafichero+ficheroMover),
                    Paths.get(rutafichero+directorioNuevo).resolve(nuevoNombre),StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void borrarFichero(){
        String ficheroBorrar;

        preguntaRuta();

        System.err.println("***CUIDADO***");
        System.err.println("BORRAR FICHERO");
        System.err.print("Usted esta en :"+rutaMain);
        ficheroBorrar = sc.nextLine();
        rutaMain += ficheroBorrar;

        File fichero = new File(rutaMain);
        if(fichero.exists()){
            if(fichero.delete()){
            System.out.print("BORRANDO PARA SIEMPRE: "+rutaMain);
            }
        }


    }
    public static void preguntaRuta(){
        if(rutaMain.equalsIgnoreCase("")){
            System.out.println("Ingrese ruta");
            rutaMain = sc.nextLine();
        }
    }
    public static void informacionFichero(File fichero){

        if(fichero.exists()){
            System.out.println("***INFORMACION FICHERO***");
            System.out.println("Nombre del fichero: "+fichero.getName());
            System.out.println("Ruta Adsoluta: "+fichero.getAbsolutePath());
            System.out.println("Tamaño: "+fichero.length());

            if (fichero.isDirectory()){
                System.out.println("es un directorio");
            }
            else{
                System.out.println("es un archivo");
            }

        }

    }
    public static void modificarRute(){
        System.out.println("Ingrese ruta");
        rutaMain = sc.nextLine();
    }
}