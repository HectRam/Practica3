/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.StringTokenizer;

/**
 *
 * @author Hector
 */
public class Operando extends Practica3{
    
    
    String Direccion(String Operando,String dir, int lin,String moddir,String codop){
        
        String  b=".err",Mdir=null;
        boolean banRel=false;
        try{
       /* File f =new File(dir+b);
        FileWriter fw=new FileWriter(f,true);
        BufferedWriter error=new BufferedWriter(fw);
        */
            System.out.println("Operando antes: "+Operando);
      if(Operando.matches("^\\%.*")||Operando.matches("^\\@.*")||Operando.matches("\\$.*")||Operando.matches("^\\#.*")||Operando.matches("^[0-9]+")||Operando.matches("^[a-zA-Z]+")||Operando.matches("^\\[.*")){
       /*   
          int x = 32678;  
          int y = ~x;   
          int z = y + 1;
        */  
          System.out.println("Codop mod: "+codop);
          //System.out.println("A2: "+z);
          //Directo
          if(Operando.matches("^\\%.+")||Operando.matches("^\\@[0-7]+")||Operando.matches("\\$[0-9A-Fa-f]*")||Operando.matches("^[0-9]+")){
          //DIR
              
              if(moddir.equals("DTV")){
                  Mdir=moddir;
              }else{
                  Mdir="DIR";
              }
              
              
              int DIR=0;
              int tam=Operando.length();
              if(Operando.matches("^\\%.+")||Operando.matches("^\\@[0-7]+")||Operando.matches("\\$[0-9A-Fa-f]*")){
                  banRel=true;
              
              String dircad=Operando.substring(1,tam);
              DIR=Integer.parseInt(dircad,16);
              }
          if(Operando.matches("\\%.*")){
              
              System.out.println("Binario "+Operando);
          }else{
              if(Operando.matches("\\@.*")){
                  
                  System.out.println("Octal "+Operando);
              }else{
                  if(Operando.matches("\\$[0-9A-Fa-f]*"))
                  {
                      banRel=true;
                      System.out.println("Hexadecimal"+Operando);
                       String Hexcad=Operando.substring(1,tam);
                       int EXT=Integer.parseInt(Hexcad,16);
                       if(EXT>=256){
                           Mdir="EXT";
                       }
                  }else{
                      int op=Integer.parseInt(Operando);
                      if(op>=256){
                          if(moddir.equals("DTV")){
                              Mdir=moddir;
                          }
                          else{
                              Mdir="Ext";
                          }
                      }
                      System.out.println("Decimal"+Operando);
                  }
                  
              }
              
          }
          
          }
          
          //IDX'S
          if(Operando.matches("^[-]*([0-9a-dA-D])*,([+|-])*([X|x|Y|y|sp|SP|pc|PC])*[+|-]*$")){
              banRel=true;
              String IDXcad=null;
              StringTokenizer IDX=new StringTokenizer(Operando,",");
              IDXcad =IDX.nextToken();
              if(IDXcad.matches("[a|A|b|B|d|D]")){
                  Mdir="IDX A";//Acumulador
              }
              
              if(IDXcad.matches("^[-]?[0-9]")){
                  banRel=true;
                  //contienen Decimales
                  int val=0;
                  val=Integer.parseInt(IDXcad);
                   // IDX 5Bits
                 if(val>=-16&&val<=15||Operando.matches(" ^,([+|-])*([X|x|Y|y|sp|SP|pc|PC])*[+|-]*$")){
                     
                     Mdir="IDX";
                 }
                 //IDX 9 Bits
                 if(val>=-256&&val<=-17||val>=16&&val<=255){
                     Mdir="IDX1";
                     
                 }
                 //IDX 16Bits
                 if(val<=-257&&val>=-32768||val>=256&&val<=65535){
                     Mdir="IDX2";
                 }
                 
              }else{
                  //Contiene Decimal
                  if(Operando.matches(",([+|-])*([X|x|Y|y|sp|SP|pc|PC])*[+|-]*$")){
                      Mdir="IDX";
                      
                  }
              }
          }
          
          //16 Bits Indirecto
          if(Operando.matches("\\[([0-9])*,([X|x|Y|y|sp|SP|pc|PC])*\\]")){
              
              Mdir="[IDX2]";
              
          }
          //Indexado Indirecto de Acumulador
          if(Operando.matches("\\[([D|d])*,([X|x|Y|y|sp|SP|pc|PC])*\\]")){
              Mdir="[D,IDX]";
              
          }
          
          //Inmediato IMM8, IMM16
          if(Operando.matches("^#.+")){
              
              int IMM=0;
              int tam=Operando.length();
              String immcad=Operando.substring(1,tam);
              //con base
              if(immcad.matches("^\\@.*")||immcad.matches("^\\%.*")||immcad.matches("^\\$.*"))
                {
                    
                  immcad=Operando.substring(2,tam);
               IMM =Integer.parseInt(immcad, 16);
              
              if(IMM<=255||-256<=IMM){
                  //System.out.println("Entro A imm8");
                  Mdir="IMM8";
                  //return Mdir;
              }
              else if(IMM<=65535||-32768<=IMM){
                  //System.out.println("Entro A imm16");
                  Mdir="IMM16";
                  //return Mdir;
              }
                }else{//sin base 
                  IMM =Integer.parseInt(immcad, 16);
                  if(IMM<=255||-256<=IMM){
                  //System.out.println("Entro A imm8");
                  Mdir="IMM8";
                  //return Mdir;
                  }
                  if(IMM<=65535||-32768<=IMM){
                   //System.out.println("Entro A imm16");    
                  Mdir="IMM16";
                  //return Mdir;
                  }
              }
          }
          
          //REL8
          if(Operando.matches("^[0-9a-zA-Z].*")&&banRel==false){
              
              if(codop.matches("^[lL].*")){
                  Mdir="REL16";
              }else{
              Mdir="REL8";
              }
          }
          
          
          
         
      } 
       else{
          /*
          error.write("Linea: "+lin+" Error el modo de Ddireccionamiento no es valido");
          error.newLine();
          */
      }
       
          
        
        }catch(Exception e){
            System.out.println("Hubo un problema en los modos de direccionamiento: "+e);
            
        }
        
      return Mdir;
    }
    
}
