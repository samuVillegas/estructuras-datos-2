import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        //Creamos el tablero de huesos
        String bones [][] = new String[7][7];
        int cont = 1;
        for(int i = 0; i < 7 ; i++){
            for(int j = i; j < 7; j++){
                bones[i][j] = String.valueOf(cont);
                bones[j][i] = String.valueOf(cont);
                cont = cont + 1;
            }
        }
        //Ejecutamos cada uno de los tests
        ArrayList<String[][]> tests = input();
        for(int i = 0; i < tests.size();i++){
            process(tests.get(i),bones,i+1);
        }  
        
    }

    // Método para leer la entrada 
    private static ArrayList<String[][]> input() {
        ArrayList<String[][]> tests = new ArrayList<>();
        Scanner in = new Scanner(System.in);  
        
        int contLines = 0;
        int contTest = 0;
        tests.add(new String[7][8]);

        while (in.hasNextLine()) {
            String line = in.nextLine();
            if(line.equals("")) break;

            if(contLines==7){
                contLines = 0;
                contTest++;
                tests.add(new String[7][8]);
            }
            tests.get(contTest)[contLines] = line.split(" ");
            contLines++;
        }
        return tests;
    }

    //Método para creación de variables y objetos, esquema de salida y llamada al método recursivo
    private static void process(String[][] test, String[][] bones, int numberLayout){
        String auxiliarMatriz [][] = new String[7][8]; // Matriz auxiliar para manejar las soluciones.
        boolean[][] visitedFields = new boolean[7][8]; //Matriz para guardar que campos ya han sido visitados.
        boolean [] activatedBones = new boolean[28]; //Arreglo para saber que hueso esta ya ha sido encontrado encontrado.    
        int[] numberSolution = {0}; //Arreglo que nos guardara el número de soluciones que de el programa

        if(numberLayout!=1) System.out.print("\n\n\n\n\n");
        System.out.print("Layout #"+numberLayout+":\n\n\n");
        for(int i = 0; i<7;i++){
            System.out.print("   ");
             for(int j = 0;j<8;j++){
                if(j==7) System.out.print(test[i][j]);
                else System.out.print(test[i][j]+"   ");
            }
            System.out.println("");
        }
        System.out.print("\nMaps resulting from layout #"+numberLayout+" are:\n\n\n");
        auxProcess(test,auxiliarMatriz,visitedFields,activatedBones,bones,0,0,numberSolution); // Llamada a método que realizará la recursividad
        System.out.println("There are "+numberSolution[0]+" solution(s) for layout #"+numberLayout+".");
    }

    //Método principal que realiza la recursión 
    private static void auxProcess(String[][]test,String[][]auxiliarMatriz,boolean[][]visitedFields, boolean activatedBones[], String[][] bones, int row,int col,int[]numberSolution){
        //Comprobamos si todos ya hay una solución. Si la hay la mandamos a imprimir sino entramos al for.
        if(!visited(visitedFields)){                  
           for(int i = 0; i<2;i++){

               // Determinamos (x+1,y) ó (x,y+1)
               int nextRow,nextCol;
               if(i==0){
                   nextRow=row+1;
                   nextCol=col;
               }else{
                   nextRow=row;
                   nextCol=col+1;
               }

                //Validamos que las coordenadas si esten dentro del rango y que el campo no esté visitado
               if(!(nextRow<0 || nextCol<0 || nextRow>= test.length || nextCol >=test[0].length || visitedFields[nextRow][nextCol])){ 

                   //Hallamos el hueso que corresponde a la union de la pareja actual y la hallada anterioirmente  
                   int actualBone = Integer.parseInt(bones[Integer.parseInt(test[row][col])][Integer.parseInt(test[nextRow][nextCol])])-1;

                   //Si el hueso no está activo procedemos a cambiar los diferentes valores para seguir recorriendo el test            
                   if(!activatedBones[actualBone]){
                       //Marcamos como visitados al campo de la pareja actual como la hallada anterioirmente
                       visitedFields[row][col] = true;
                       visitedFields[nextRow][nextCol] = true;

                        //Asignamos a la matriz solución el hueso correspondiente
                       auxiliarMatriz[row][col] = String.valueOf(actualBone+1);
                       auxiliarMatriz[nextRow][nextCol] = String.valueOf(actualBone+1);
                       //Ponemos el hueso como activo, es decir que ya se utilizó
                       activatedBones[actualBone] = true;
                        // Buscamos un campo que no esté visitado para buscar más valores
                       String nextField[] = nextField(visitedFields).split(" ");
                       //Hacemos la recursión con el campo encontrado
                        auxProcess(test,auxiliarMatriz,visitedFields,activatedBones,bones,Integer.parseInt(nextField[0]),Integer.parseInt(nextField[1]),numberSolution);
                        //Luego de que se realice el proceso de recursion necesitamos poner los campos como no visitados el hueso inactivo para que se puedan seguir encontrando soluciones. 

                    
                       visitedFields[row][col] = false;
                       visitedFields[nextRow][nextCol] = false;
                       activatedBones[actualBone] = false;
                   }
               }
           }
        }else{
            printSolution(auxiliarMatriz);
            numberSolution[0]++;
        }
    }

    //Método para comprobar si hay o no saolución
    private static boolean visited(boolean[][] visitedFields){
        for(int i = 0;i<7;i++){
            for(int j = 0;j<8;j++){
                if(!visitedFields[i][j]) return false;
            }
        }
        return true;
    }
    //Método para conseguir el siguiente campo
    private static String nextField(boolean[][] visitedFields){
        for(int i = 0;i<7;i++){
            for(int j = 0;j<8;j++){
                if(!visitedFields[i][j]) return i+" "+j;
            }
        }
        return "1007876 10075686";
    }
    //Método para imprimir la solución encontrada
    private static void printSolution(String[][] solution){
        for(int i = 0; i<7;i++){
             for(int j = 0;j<8;j++){
                if(solution[i][j].length()==1)System.out.print("   "+solution[i][j]);
                else System.out.print("  "+solution[i][j]);    
            }
            System.out.println("");
        }
        System.out.print("\n\n");
    }

}