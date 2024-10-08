package Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class FileController {
    private Scanner sc = new Scanner(System.in);
    private String rutafichero = "";
    private String op = " ";
    private String sistemOperativo = "linux";

    public FileController() {
    }

    public String getRutafichero() {
        return rutafichero;
    }

    public void setRutafichero(String rutafichero) {
        this.rutafichero = rutafichero;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getSistemOperativo() {
        return sistemOperativo;
    }

    public void setSistemOperativo(String sistemOperativo) {
        this.sistemOperativo = sistemOperativo;
    }

    public void menu() {
        System.out.println("Bienvenido al sistema");

        while (!op.equalsIgnoreCase("s")) {
            System.out.print("***TEST***" +
                    "\nls) LISTAR" +
                    "\nrm) BORRAR" +
                    "\nmv) MOVER O CAMBIAR NOMBRE FICHERO(Rename)" +
                    "\nmf) MOVER FICHERO (File.move)" +
                    "\ncd) MODIFICAR RUTA" +
                    "\ni) INFORMACION FICHERO" +
                    "\nsy) SISTEMA OPERATIVO" +
                    "\n1) TEST01" +
                    "\n2) TEST02" +
                    "\ns) SALIR" +
                    "\n:");
            op = sc.nextLine();
            switch (op) {
                case "1":
                    test1();
                    break;
                case "2":
                    test02();
                    break;
                case "rm":
                    borrarFichero();
                    break;
                case "ls":
                    imprimirDirectorio();
                    break;
                case "mv":
                    moverRenombrarFileRename();
                    break;
                case "i":
                    preguntaRuta();
                    informacionFichero(new File(rutafichero));
                    break;
                case "cd":
                    modificarRuta();
                    break;
                case "mf":
                    moverFicheroConFilesRename();
                    break;
                case "sy":
                    sistemaOperativo();
                    break;
                case "s":
                    break;
            }
        }
    }

    public void test1() {
        System.out.println("Introduzca la ruta en la que se creará la estructura");

        preguntaRuta();

        rutafichero += "/javatest";

        File ficheroMain = new File(rutafichero);
        if (ficheroMain.exists()) {
            imprimirDirectorio();
        } else {

            ficheroMain.mkdir();
            File d1 = new File(rutafichero + "/d1");
            d1.mkdir();
            File d2 = new File(rutafichero + "/d2");
            d2.mkdir();
            File f11 = new File(rutafichero + "/d1/f11.txt");
            File f21 = new File(rutafichero + "/d2/f21.txt");
            try {
                f11.createNewFile();
                f21.createNewFile();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Se a creado la extructura");

        }

    }

    public void test02() {
        preguntaRuta();
        File usr01 = new File(rutafichero + "/usr01");
        File usr02 = new File(rutafichero + "/usr02");
        File f1 = new File(usr01 + "/f1.txt");
        File d1 = new File(usr02 + "/d1");
        File d2 = new File(usr02 + "/d2");
        File f2 = new File(d1 + "/f2.txt");
        File f3 = new File(d2 + "/f3.txt");

        usr01.mkdir();
        usr02.mkdir();
        d1.mkdir();
        d2.mkdir();

        try {
            f1.createNewFile();
            f2.createNewFile();
            f3.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void imprimirDirectorio() {
        preguntaRuta();
        System.out.println("IMPRIMIENDO " + rutafichero);

        //Creamos un objeto fichero con esa ruta
        File fichero = new File(rutafichero);

        File[] ficheros = fichero.listFiles();
        //Verificacmos si el fichero existe
        if (fichero.exists()) {
            //Recorremos y comprobamos si es fichero y si es un directorio
            for (File file : ficheros) {

                if (fichero.isFile()) {
                    System.out.println("-" + file.getName() + " es un fichero");
                } else if (fichero.isDirectory()) {
                    System.out.println("*" + file.getName() + " es un directorio");

                    File[] subdirectorios = file.listFiles();
                    if (subdirectorios != null) {
                        for (File subdir : subdirectorios) {
                            //
                            if (subdir.isFile()) {
                                System.out.println("\t-" + subdir.getName() + " es un fichero");
                            } else if (subdir.isDirectory()) {
                                System.out.println("\t-" + subdir.getName() + " es un directorio");

                            }

                        }
                    }
                }
            }
            if (op.equalsIgnoreCase("1")) moverRenombrarFileRename();

        } else {
            System.out.println("El fichero no existe.");
            modificarRuta();
        }
    }

    public void moverRenombrarFileRename() {
        String ficheroCambiar, ficheroNuevo;
        preguntaRuta();

        System.out.println("CAMBIAR NOMBRE FICHEROS O MOVER ENTRE DIRECTORIOS");
        System.out.print("***Inserte la ruta del archivo que desea cambiar el nombre*** " +
                "\nUsted esta en :$" + rutafichero);
        ficheroCambiar = sc.nextLine();
        File fichero = new File(rutafichero + ficheroCambiar);
        if (fichero.exists()) {
            System.out.print("***Introduzca la ruta y el nuevo nombre del archivo***. " +
                    "\nUsted esta en :$" + rutafichero);
            ficheroNuevo = sc.nextLine();
            File nuevaRuta = new File(rutafichero + ficheroNuevo);

            fichero.renameTo(nuevaRuta);
            System.out.println("Se cambio el nombre del fichero " + ficheroCambiar + " a " + ficheroNuevo);

        } else {
            System.out.println("El fichero no existe.");
            modificarRuta();
        }
    }

    public void moverFicheroConFilesRename() {
        String ficheroMover, directorioNuevo, nuevoNombre;

        preguntaRuta();

        System.out.println("MOVER FICHERO");
        System.out.print("Introduzca el fichero que quiere mover\nUsted esta en :$" + rutafichero);
        ficheroMover = sc.nextLine();
        System.out.print("Introduzca la ruta a donde quieres mover el fichero /ruta \nUsted esta en :$" + rutafichero);
        directorioNuevo = sc.nextLine();

        String[] s = ficheroMover.split("/");

        nuevoNombre = s[s.length - 1];
        System.out.println("Se movio el fichero:" + nuevoNombre + " a " + directorioNuevo);
        //agregar cuandi esta vacio
        try {
            Files.move(Paths.get(rutafichero + ficheroMover),
                    Paths.get(rutafichero + directorioNuevo).resolve(nuevoNombre),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void borrarFichero() {
        String ficheroBorrar;

        preguntaRuta();

        System.err.println("***CUIDADO***");
        System.err.println("BORRAR FICHERO O DIRECTORIOS ");
        System.err.print("Usted esta en :" + rutafichero);
        ficheroBorrar = sc.nextLine();
        rutafichero += ficheroBorrar;

        File fichero = new File(rutafichero);
        if (fichero.exists()) {

            if (fichero.delete()) {
                System.out.println("BORRANDO PARA SIEMPRE: " + rutafichero);
                System.out.println("SU RUTA A CAMBIADO INGRESE LA NUEVA RUTA");
                modificarRuta();
            } else {
                System.out.println("El fichero no esta vacio. INTENTALO DE NUEVO");
                borrarFichero();
            }


        } else {
            System.out.println("Fcihero no Existe");
            modificarRuta();
        }


    }

    public void preguntaRuta() {
        if (rutafichero.equalsIgnoreCase("")) {
            System.out.print("Ingrese ruta" +
                    "\n$");
            rutafichero = sc.nextLine();
        }
    }

    public void informacionFichero(File fichero) {
        long ultaModificacion = fichero.lastModified();
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        if (fichero.exists()) {
            System.out.println("***INFORMACION FICHERO***");
            System.out.println("Nombre del fichero: " + fichero.getName());
            System.out.println("Ruta Adsoluta: " + fichero.getAbsolutePath());
            System.out.println("Tamaño: " + fichero.length());

            if (fichero.isDirectory()) {
                System.out.println("Tipo Archivo : directorio");
            } else {
                System.out.println("Tipo Archivo : archivo");
            }
            System.out.println("Ultima modificacion : " + date.format(ultaModificacion));
            System.out.println("Permisos de ejecucion: " + fichero.canExecute());
            System.out.println("Permisos de lectura: " + fichero.canRead());
            System.out.println("Permisos de escritura: " + fichero.canWrite());

        } else {
            System.out.println("El fichero no existe.");
            modificarRuta();
        }

    }

    public void modificarRuta() {
        System.out.print("Ingrese ruta" +
                "\n$");
        rutafichero = sc.nextLine();
        System.out.println("***SE MODIFICO LA RUTA***");
    }

    public void sistemaOperativo() {
        preguntaRuta();
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains(sistemOperativo)) {
            informacionFichero(new File(rutafichero));
        } else {
            System.out.println("Sistema operativo: " + os);
            System.out.println("solo se muetra a usuarios de " + sistemOperativo);
        }
    }
}
