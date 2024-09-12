import java.io.*;
import java.util.Scanner;

public class Main{
    // Método para crear el archivo
    static File crearArchivo(String nombreArchivo){
        String ruta = System.getProperty("user.home") + "/Desktop/"+ nombreArchivo + ".txt";
        File archivo = new File(ruta);

        try{
            if(archivo.createNewFile()){
                System.out.println("✅ El archivo " + nombreArchivo + ".txt ha sido creado en el escritorio.");
            }else{
                System.out.println("⚠\uFE0F El archivo ya existe en el escritorio."); // PENDIENTE EL ÍCONO
            }
        }catch(IOException e){
            System.out.println("❌ Error al crear el archivo." + e.getMessage());
        }
        return archivo;
    }

    // Método para ingresar la información
    static void guardarInformacion(File archivo){
        System.out.println("\n📑 Ingrese la información que desea para guardar en el archivo: ");

        Scanner teclado = new Scanner(System.in);
        String linea = teclado.nextLine();

        try(BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo, true))) {
            escritor.write(linea);
            escritor.newLine();
            System.out.println("✅ Información guradada exitosamente en el archivo.");
        }catch (IOException e){
            System.out.println("❌ Error al guardar la información" + e.getMessage());
        }
    }

    // Leer contenido
    static String leerArchivo(File archivo){
        StringBuilder contenido = new StringBuilder();

        try(BufferedReader lector = new BufferedReader(new FileReader(archivo))){
            String linea;
            while((linea = lector.readLine()) != null){
                contenido.append(linea).append("\n");
            }
        }catch(IOException e){
            System.out.println("❌ Erroe al leer el archivo");
        }

        return contenido.toString().trim();
    }

    // Cifrar texto
    static String CifrarTexto(String texto, int desplazamiento){
        StringBuilder textoCifrado = new StringBuilder();

        for(char caracter : texto.toCharArray()){
            textoCifrado.append((char) (caracter + desplazamiento));
        }

        return textoCifrado.toString();
    }

    // Descrifar texto
    static String descrifrarTexto(String texto, int desplazamiento){
        StringBuilder textoDescifrado = new StringBuilder();

        for(char caracter : texto.toCharArray()){
            textoDescifrado.append((char) (caracter - desplazamiento));
        }

        return textoDescifrado.toString();
    }

    // Guardar el texto en un nuecho archivo
    static void guardarNuevoArchivo(String contenido, String nombreArchivo){
        String ruta = System.getProperty("user.home") + "/Desktop/" + nombreArchivo + ".txt";

        try(BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta))){
            escritor.write(contenido);
            System.out.println("✅ El contenido ha sido guardado en el archivo " + nombreArchivo + ".txt en el escritorio");
        }catch (IOException e){
            System.out.println("❌ Error al guardar el nuevo archivo" + e.getMessage());
        }
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        File archivoOriginal;

        // Preguntar sobre el archivo existente
        System.out.println("---------------------------------------------------");
        System.out.println("⚙️ Bienvenido al Gestor de Archivos y Cifrado ⚙️");
        System.out.println("---------------------------------------------------");

        System.out.print("\n¿Ya tiene un archivho para guardar la información? (si/no): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();

        // Crear o usar un archivo existente
        if(respuesta.equals("si")){
            System.out.print("🔎 Ingrese la ruta completa del archio existente: ");
            String rutaCompleta = scanner.nextLine();
            archivoOriginal = new File(rutaCompleta);

            if(!archivoOriginal.exists()){
                System.out.println("⚠\uFE0F El archio no existe. Creando un nuevo en el escritorio.");
                System.out.print("📑 Ingrese el nombre del nuevo archivo: ");
                String nombreArchivo = scanner.nextLine();
                archivoOriginal = crearArchivo(nombreArchivo);
            }
        }else{
            System.out.print("\uD83D\uDCDD Ingrese el nombre del archivo a crear en el escritorio: ");
            String  nombreArchivo = scanner.nextLine();
            archivoOriginal = crearArchivo(nombreArchivo);
        }

        // Guardar información en el archivo original
        guardarInformacion(archivoOriginal);

        // Leer el contenido original
        String contenidoOriginal = leerArchivo(archivoOriginal);
        System.out.print("______________________________________________________________________");
        System.out.println("\n\uD83D\uDCDD Contenido original: \n" + contenidoOriginal);
        System.out.println("______________________________________________________________________");
        System.out.println("\n");

        // Cifrar contenido y guardarlo
        int desplazamiento = 3;
        String contenidoCifrado = CifrarTexto(contenidoOriginal, desplazamiento);
        guardarNuevoArchivo(contenidoCifrado, "ArchivoCifrado");

        // Leer archivo cifrado y descifrarlo
        File archivoCifrado = new File(System.getProperty("user.home") + "/Desktop/ArchivoCifrado.txt");
        String contenidoLeidoCifrado = leerArchivo(archivoCifrado);
        String contenidoDescifrado = descrifrarTexto(contenidoLeidoCifrado, desplazamiento);
        guardarNuevoArchivo(contenidoDescifrado, "ArchivoDescifrado");

        // Verificar el contenido
        if(contenidoOriginal.equals(contenidoDescifrado)){
            System.out.println("\n✅ La descodificación fue exitosa. El contenido es igual al original");
        }else{
            System.out.println("\n❌ La descodificación falló. El contenido no coincide con el original");
        }

        scanner.close();
    }
}
