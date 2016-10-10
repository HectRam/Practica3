/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3;

/**
 *
 * @author Hector
 */
import java.io.*;
import java.io.BufferedReader;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.FileReader;
public class Practica3 {
    
    /**
     * @param args the command line arguments
     */
    
    public void leer()
    {
        
        Scanner s = new Scanner(System.in);
        String thisLine,dir,a=".asm",b=".err",i=".inst",ax=null;
        thisLine = null;
        int poslin=0,c=0,pos=0,banbuffer=0,compara=0,banCod=0,sioperI=2;
        espacios es;
        Operando op;
        String etiqueta = null, codop = null, operando = null, comentario=null,linToken=null,codoplin=null,sioperS=null,codopprue=null,Mdir=null;
        String exEt=null,exCod=null,moddir=null,codcal=null,bytescal=null,bytesxcal=null,totbytes=null,samecod=null,dircod=null,samecod2=null;
        Vector<String> cadena;
        cadena = new Vector<>();
        System.out.print("Ruta del archivo? ");
          dir= s.nextLine();
         /**
          * System.out.print(dir);
          */
                try{
             FileInputStream fstr = new FileInputStream(dir+a);
             DataInputStream input = new DataInputStream(fstr);
            BufferedReader lectb = new BufferedReader(new InputStreamReader(input));
            //escribe en el archivo inst
            File ins =new File(dir+i);
            FileWriter fwins=new FileWriter(ins,true);
            BufferedWriter instrucciones=new BufferedWriter(fwins);
            //escribe en el archivo errores
            File f =new File(dir+b);
            FileWriter fw=new FileWriter(f,true);
            BufferedWriter error=new BufferedWriter(fw);
            //escribe en el archivo comentarios
            File f2com =new File("comentarios"+a);
            FileWriter fwcom=new FileWriter(f2com,true);
            BufferedWriter comentarios=new BufferedWriter(fwcom);
           // StringTokenizer Token = new StringTokenizer(dir+a);
            boolean banEnd,espacio,banCom,banderalim,errBan,banEt,errtab=false;
            banEnd = false;
            espacio = false;
            banCom= false;
            errBan=false;
            banderalim=false;
            banEt=false;
            //System.out.println("Linea---ETQ-----CODOP-----OPER---");
            instrucciones.write("Linea---ETQ-----CODOP-----OPER-----modos");
            instrucciones.newLine();
             while((thisLine = lectb.readLine()) != null && banEnd != true){ //empieza a leer las lineas en loop
                        es = new espacios();
                        op = new Operando();
                        codop=" ";
	            	operando=" ";
	            	etiqueta=" ";
	                c++;
                       comentario=" ";    
                       
                  // System.out.println("Linea       \n"+thisLine);
                   StringTokenizer Token = new StringTokenizer(thisLine);
                   
                   while(Token.hasMoreTokens())
                   {
                       
                   linToken=Token.nextToken();
                    
                   //manda a la clase publica espacio
                     espacio = es.spacio(thisLine);
                 /* if(thisLine.charAt(0) == ' ' || thisLine.charAt(0) == '\t'){
                             
                            
                            espacio = true; 
                            
                       
                         }
                  else{
                      espacio = false;
                  }*/
                 /**
                  * comentario (empiezan con ; puede ir seguido de cualquier digito  del cero al nueve, cualquier caracter de la 'a' a la 'z' incluyendo mayusculas )
                  */
                    if(linToken.matches(".*[;].*")){

                         poslin=thisLine.indexOf(';');
                         
                      // System.out.println("Posicion linea del comentario   "+poslin+1);
                         comentario=thisLine.substring(poslin+1);
                         
                         StringTokenizer at = new StringTokenizer(comentario);
                         linToken=" ";
                         while(at.hasMoreTokens()){
                             ax=at.nextToken();
                     //      System.out.println("Ax    "+ax);
                            cadena.add(ax);
                           comentarios.write(ax+" ");
                         }
                         comentarios.newLine();
                         linToken=" ";
                         banCom= true;
                     }
                      /**
                       * EntraEtiqueta
                       */
                       
                         if(linToken.matches("^[a-zA-Z]{1,8}[^;]{0,1}[\\w]$")&&espacio==false&&banCom==false)
                         {
                           //  System.out.println("Etiqueta "+linToken);
                             
                         }
                         /**
                         * Entra Codop
                         */
                                if(linToken.matches("[a-zA-Z]{1,4}(?!\\d )[/.]{0,1}[a-zA-Z]$")&&banCom==false){
                         /**
                          *Inicia practica 2
                          */
                       //  System.out.println("Lintok "+linToken);
                
                         
                         
                         String TABOP="TABOP";
                         String mayus;
                         try{
                             FileInputStream fsaux = new FileInputStream(TABOP+a);
                             DataInputStream dsaux = new DataInputStream(fsaux);
                             BufferedReader  braux = new BufferedReader(new InputStreamReader(dsaux));
                             
                             String linaux;
                             //System.out.println("Tab lin "+linToken);
                             
                             while((linaux = braux.readLine())!= null){
                                 
                                 StringTokenizer aucod = new StringTokenizer(linaux,"|");
                                        mayus=linToken;
                                   exCod=aucod.nextToken();
                                 //  System.out.println("Tabop: "+exCod);
                                   errtab=true;
                                   if(exCod.compareTo(mayus.toUpperCase())==0&&mayus!="null"&&mayus!=null&&mayus!=" "){
                                       errtab=false;
                                       codop=linToken;
                                       if(codop!="null"&&codop!=null&&codop!=" "){
                                           
                                       
                                   //     System.out.println("Codop comparado "+codop);
                                       banCod=1;
                                       
                                     //  System.out.println("Auxiliar "+linaux);
                                       
                                         sioperS=aucod.nextToken("|");    //Vrifica si lleva operando
                                        sioperI=Integer.parseInt(sioperS); //convierte de String a Cadena
                                       moddir=aucod.nextToken("|");   //Modo de direccionamiento  
                                       codcal=aucod.nextToken("|");  //Codigo calculado
                                       bytescal=aucod.nextToken("|"); //Bytes calculados
                                       bytesxcal=aucod.nextToken("|");  //Bytes por calcular
                                       totbytes=aucod.nextToken("|");  //Total de bytes
                            /*           System.out.print("Codop: "+codop);
                                       System.out.print(" Modo de direccionamiento: "+moddir);
                                       System.out.print(" Codigo calculado: "+codcal);
                                       System.out.print(" Bytes calculados: "+bytescal);
                                       System.out.print(" Bytes por calcular: "+bytesxcal);
                                       System.out.println(" Total de bytes: "+totbytes);
                              */       
                                       
                                           dircod=codop+a;
                                           if(dircod!="null.asm")
                                           {
                                           File fcod =new File(dircod);
                                           FileWriter fwcod=new FileWriter(fcod,true);
                                           BufferedWriter modosdir=new BufferedWriter(fwcod);
                                           if(moddir.equals("INH")){
                                               modosdir.write(moddir+" ");
                                           }
                                           if(moddir.equals("DTV")){
                                               modosdir.write(moddir+" ");
                                           }
                                           modosdir.close();
                                           }
                                       }
                                   }
                                   
                                   
                                   
                             }
                             dsaux.close();
                             
                         }catch(Exception r){
                             System.out.println("Hubo un error en el Tabop "+r);
                         }
                         //Etiqueta alterna
                         
                         //etiqueta="null";
                         codopprue=linToken;
                         if(codopprue.matches("^[a-z]{1,4}")&&!"equ".equals(codopprue)&&espacio==false&&codopprue!=codop){
                             
                            etiqueta=codopprue;
                            System.out.println("Eticod: "+codopprue);
                              codopprue="null";
                              banEt=true;
                        }//termina practica 2
                         }
                                /**
                                  * Entra Operando
                                  */  
                             else{
                                 
                     /**
                       
                      if(linToken.matches("^[a-z](?!\\d ){0,}[/.]{0,1}.{1,5}")){
                        
                        
                             System.out.println("residuo cod "+linToken);
                             
                                      }
                      */               
                                /**
                                  * Entra Operando
                                  */  
                                 if(codop!=" "&&linToken!=codop&&banCom==false){
                                  
                                     operando=linToken;
                                 //    System.out.println("Operando  "+operando);
                                    
                                    Mdir = op.Direccion(operando,dir,c,moddir);
                                     System.out.println("Modo "+Mdir);
                                     
                                     if(codop.equals(" ")){
                                         
                                         codop=linToken;
                                         
                                         
                                       // System.err.println("Error Linea: "+c+"Hubo un error en el operando "+codop);
                                        error.write("Error Linea: "+c+" Hubo un error en el operando "+codop);
                                        error.newLine();
                                         operando=" ";
                                         errBan=true;
                                     }
                                     /**
                                      * Cierra Operando
                                      */
                                 }
                                 else
                                 {
                                     /**
                                      * Espacio
                                      */
                                     
                                   //  thisLine.split("\\s");
                                     //entra a etiqueta
                                     if(espacio==false)
                                     {     
                                           // thisLine.split(";");
                                         pos=0;
                                         exEt=" "; 
                                     
                                        // exEt=thisLine.substring(0,pos);
                                        // System.out.println("Pos "+pos);
                                         if(linToken.matches("^[a-zA-Z]{1,8}[^;]{0,1}[\\w]$")&&banCom==false&&codop!=linToken)
                                         {
                                             
                                           // System.out.println("Lin token eti: "+linToken);
                                           // etiqueta=linToken;
                                            if(poslin!=0&&thisLine.charAt(poslin)!=' '&&poslin>2){
                                            //   System.out.println("com pos"+thisLine.charAt(poslin)+"Npos "+poslin);
                                               exEt=thisLine.substring(0,poslin);
                                               //System.out.println("Etiqueta "+exEt);
                                            }
                                            
                                            //System.out.println("Et despues! "+exEt);
                                           //if(linToken.equals(exEt)){
                                               
                                               etiqueta=linToken;
                                             //  System.out.println("etiqueta  "+etiqueta);
                                               
                                               
                                              
                                          //}      
  
                                         
                                     }
                                      
                                     }
                                     
                                     cadena.clear();
                                     
                                     
                                 }
                                     
                                     
                                     
                             }
                         
                             
                     
                        
                        
                        
                   }
                   
                   if(codop==" "){
                       codop="null";
                   }
                   if(etiqueta==" ")
                   {
                       etiqueta="null";
                   }
                   if(operando==" "){
                     operando="null";
                     }
                  
                  if(sioperI==1&&operando=="null"){
                      error.write("Linea: "+c+" Error la instruccion del codop debe de tener operando");
                      error.newLine();
                      errBan=true;
                  }
                  if(sioperI==0&&operando!="null"){
                      error.write("Linea: "+c+" Error la instruccion del codop no debe de tener operando");
                      error.newLine();
                      errBan=true;
                  }
                  if(operando!="null"&&codop=="null"&&etiqueta!="null")
                  {
                     // System.out.println("Linea: "+c+" Error no se puede tener tener etiqueta u operando sin codop");
                      error.write("Linea: "+c+" Error no se puede tener tener etiqueta y operando sin codop ");
                      error.newLine();
                      errBan=true;
                      
                  }
                  if(operando=="null"&&codop=="null"&&etiqueta!="null"){
                     error.write("Linea: "+c+" Error no se puede tener tener etiqueta sin codop");
                     error.newLine();
                     errBan=true;
                  }
                  if(operando!="null"&&codop=="null"&&etiqueta=="null")
                  {
                      error.write("Linea: "+c+" Error no se puede tener tener operando sin codop");
                      error.newLine();
                      errBan=true;
                  }
                  if(banCod==0){
                      error.write("Linea: "+c+" Error codigo no encontrado");
                      error.newLine();
                      errBan=true;
                      banderalim=true;
                  }
                  if(errtab!=false&&etiqueta=="null"&&banCod==0&&banderalim==false){
                                 error.write("Error Linea: "+c+" Operando no valido");
                                 error.newLine();
                                 errBan=true;
                             }
                     if(banCod==1&&errBan==false){
                      
                     samecod2=codop+a;
                     //System.out.println("codop arch: "+samecod2);
                     if(samecod2!="null.asm")
                     {
                      File f2 =new File(samecod2);
                      FileInputStream fcod2 = new FileInputStream(samecod2);
                      DataInputStream inputcod2 = new DataInputStream(fcod2);
                      BufferedReader brcod2 = new BufferedReader(new InputStreamReader(inputcod2));
                      codoplin=brcod2.readLine();//imprime los modos de direccionamiento
                      
                      
                      
                //  System.out.println(c+"  ee  "+etiqueta+"  cc  "+codop+"  oo  "+operando);
                  instrucciones.write(c+"      "+etiqueta+"      "+codop+"      "+operando+"      "+codoplin);
                  instrucciones.newLine();
                  brcod2.close();
                  f2.delete();
                       }
                       }
                     if(errBan==true)
                     {
                         if(codop!="null"){
                             
                         
                         samecod2=codop+a;
                    // System.out.println("codop arch: "+samecod2);
                     if(samecod2!="null.asm")
                     {
                      File f2 =new File(samecod2);
                      FileInputStream fcod2 = new FileInputStream(samecod2);
                      DataInputStream inputcod2 = new DataInputStream(fcod2);
                      BufferedReader brcod2 = new BufferedReader(new InputStreamReader(inputcod2));
                      codoplin=brcod2.readLine();
                      
                      brcod2.close();
                      f2.delete();
                     }
                         }
                     }
                     //  System.out.println(thisLine);//muestra temporal
                     errtab=false;
                     banCom=false;
                     errBan=false;
                     banderalim=false;
                     banCod=0;
                     sioperI=2;
                     if(codop.equals("END")||codop.equals("End")||codop.equals("end")){//verifica si tiene End
                           banEnd = true;
                       }
                      
                    }
                    if(banEnd==false)
                       {
                         //  System.err.println("Linea: "+c+"Error: no se encontro el final del archivo(End)");
                          error.write("Error: no se encontro el final del archivo(End)");
                           error.newLine();
                       }
                //System.out.println("Fin del recorrido");   
             //  fw.close();
             comentarios.close();
               error.close();
               instrucciones.close();
            //   comentarios.close();
                }catch(Exception e){
                    System.err.println("Hubo un error en el codigo\n"+e);
                    
    }
        
        
    }
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Practica3 H = new Practica3();
         H.leer();
        
    }
    
    
}
