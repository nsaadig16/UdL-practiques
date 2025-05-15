/* -----------------------------------------------------------------------
 PRA1. Processos, pipes i senyals: Primers
 Codi font: controlador.c
 Nom complet Naïm Saadi Gallego
 Nom complet Eduard Térmens Botanch
 ---------------------------------------------------------------------- */

/*
----------------------------------------------------------------------
--- L L I B R E R I E S ------------------------------
----------------------------------------------------------------------
*/
#include <stdio.h> /* sprintf*/

// fork, pid_t, wait, ..
#include <sys/types.h>
#include <unistd.h>

#include <stdlib.h> /* exit, EXIT_SUCCESS, ...*/
#include <string.h> /* strlen */

#include <sys/wait.h> /* wait */
#include <errno.h>    /* errno */
#include <signal.h>

/*
----------------------------------------------------------------------
--- C O N S T A N T S------ ------------------------------------------
----------------------------------------------------------------------
*/

#define MIDA_MAX_CADENA 1024

#define INVERTIR_COLOR "\e[7m"
#define FI_COLOR "\e[0m"
#define MIDA_MAX_CADENA_COLORS 1024
#define FORMAT_TEXT_ERROR "\e[1;48;5;1;38;5;255m"
#define PIPE_ESCRIPTURA_NOMBRES 10      //Definim els descriptors relacionats amb els pipes
#define PIPE_LECTURA_NOMBRES 11
#define PIPE_ESCRIPTURA_RESPOSTES 20
#define PIPE_LECTURA_RESPOSTES 21

/*
----------------------------------------------------------------------
--- C A P Ç A L E R E S   D E   F U N C T I O N S --------------------
----------------------------------------------------------------------
*/
void ImprimirInfoControlador(char *text);
void ImprimirError(char *text);
void tractament_senyals();

/*
----------------------------------------------------------------------
--- D E F I N I C I Ó   D E   T I P U S ------------------------------
----------------------------------------------------------------------
*/
typedef enum
{
    FALS = 0,
    CERT
} t_logic;

typedef struct
{
 int pid;
 int nombre;
 t_logic esPrimer;
} t_infoNombre;         //Estructura que s'ha de retornar al controlador

/*
----------------------------------------------------------------------
--- V A R I A B L E S   G L O B A L S --------------------------------
----------------------------------------------------------------------
*/
char capInfoControlador[MIDA_MAX_CADENA];

/*
----------------------------------------------------------------------
--- P R O G R A M A   P R I N C I P A L ------------------------------
----------------------------------------------------------------------
*/
int main(int argc, char *argv[])
{
    unsigned short int numCalculadors;
    unsigned short int numLimit;
    unsigned char i;
    pid_t pid;
    pid_t pidGenerador;
    int estatWait;
    char cadena[MIDA_MAX_CADENA];
 
    if (argc != 3)
    {
        sprintf(cadena, "Us: %s <nombre processos calculadors> <darrer nombre enter per a la seqüència 2 a M> \n\nPer exemple: %s 3 11\n\n", argv[0], argv[0]);

        write(1, cadena, strlen(cadena));
        exit(2);
    }

    tractament_senyals();
 
    sprintf(capInfoControlador, "[%s-pid:%u]> ", argv[0], getpid());

    numCalculadors = atoi(argv[1]);
    numLimit = atoi(argv[2]);   // Nombre M de numeros primers a crear

    ImprimirInfoControlador("* * * * * * * * * *  I N I C I  * * * * * * * * * *\n");

    sprintf(cadena, "Processos calculadors: %u.\n\n", numCalculadors);
    ImprimirInfoControlador(cadena);

    int pipeRespostes[2];   
    int pipeNombres[2];     //Creació de pipes

    sprintf(cadena,"Error en crear el pipe.\n");
    if(pipe(pipeRespostes) == -1){ImprimirError(cadena); exit(-1);}
    if(pipe(pipeNombres) == -1){ImprimirError(cadena); exit(-1);}   //Control de error dels pipes

    dup2(pipeNombres[1],PIPE_ESCRIPTURA_NOMBRES);   //Aquí dupliquem els pipes als descriptors de fitxers i seguidament els tanquem.
    close(pipeNombres[1]);
    dup2(pipeNombres[0], PIPE_LECTURA_NOMBRES);
    close(pipeNombres[0]);
    dup2(pipeRespostes[1], PIPE_ESCRIPTURA_RESPOSTES);
    close(pipeRespostes[1]);
    dup2(pipeRespostes[0],PIPE_LECTURA_RESPOSTES);
    close(pipeRespostes[0]);
    
    switch (pidGenerador = fork())     //Creem el fill que executa el generador
    {
    case -1:
        ImprimirError("ERROR creació generador.\n");
    case 0:
        close(PIPE_LECTURA_RESPOSTES);  //Tanquem els pipes no utilitzats
        close(PIPE_LECTURA_NOMBRES);
        close(PIPE_ESCRIPTURA_RESPOSTES);
        sprintf(cadena,"%u",numLimit);
        execl("./generador","generador", cadena, NULL);    //Executem el generador passant com a paràmetre el número límit
        
        ImprimirError("Error excl generador.\n");
    }
    sprintf(cadena, "Activació generador (pid: %u)\n",pidGenerador);
    ImprimirInfoControlador(cadena);

    pid_t pids[numCalculadors]; //Aquí anem guardant els pids dels fills calculadors
    int primersExit = 0;

    for (i = 0; i < numCalculadors; i++) {
        switch (pid = fork()) {
        case -1:
            sprintf(cadena, "ERROR creacio fill %u.\n", i + 1);
            ImprimirError(cadena);
        case 0:
            close(PIPE_ESCRIPTURA_NOMBRES); //Tanquem els pipes no utilitzats
            close(PIPE_LECTURA_RESPOSTES);
            sprintf(cadena, "%u", i + 1);
            execl("./calculador", "calculador", cadena, NULL); //Executem els calculadors passant com a paràmetre els seu número de calculador

            sprintf(cadena, "Error execl fill %u.\n", i + 1);
            ImprimirError(cadena);
        default:
            sprintf(cadena, "Activacio calculador %u (pid: %u)\n", i + 1, pid);
            ImprimirInfoControlador(cadena);
            pids[i] = pid;  //Guardem en l'array el pid corresponent
        }
    }
    close(PIPE_ESCRIPTURA_NOMBRES); //Tanquem les pipes que no utilitzarem
    close(PIPE_LECTURA_NOMBRES);
    close(PIPE_ESCRIPTURA_RESPOSTES);

    int primersPipe = 0;
    t_infoNombre info;

    while (read(PIPE_LECTURA_RESPOSTES, &info, sizeof(info)) > 0) { //Anem llegint del pipe de respostes fins que rebem un EOF
        sprintf(cadena, "Rebut del Calculador PID %u : nombre %i és primer? ", info.pid, info.nombre);
        if (info.esPrimer == CERT) {
            primersPipe += 1;
            strcat(cadena, "Sí\n");
        } else {
            strcat(cadena, "No\n");
        }
        ImprimirInfoControlador(cadena);    //Recullim la informació de la estructura i la mostrem per pantalla.
    }

    if (kill(pidGenerador, SIGTERM) < 0) {  //Enviem la senyal SIGTERM al generador
        ImprimirError("Generador terminat incorrectament.\n");
    }
    wait(&estatWait);   //Esperem al exit del generador

    for (int i = 0; i < numCalculadors; i++) {  //Enviem la senyal SIGTERM als calculadors
        sprintf(cadena, "Calculador %i terminat incorrectament.\n", pids[i]);
        if (kill(pids[i], SIGTERM) < 0) {
            ImprimirError(cadena);
        }
        wait(&estatWait); //Esperem al exit dels calculadors
        primersExit += WEXITSTATUS(estatWait);  //Anem sumant el codi d'acabament dels calculadors
    }

    sprintf(cadena, "nombrePrimersPipe = %i  nombrePrimersExit = %i\n", primersPipe, primersExit);  //Mostrem els primers trobats per les pipes i pels codis d'acabament
    ImprimirInfoControlador(cadena);
    ImprimirInfoControlador("* * * * * * * * * *  F I  * * * * * * * * * *\n");

    exit(EXIT_SUCCESS);
}

void tractament_senyals(){  //Ignorem les senyals SIGINT i SIGQUIT
    if(signal(SIGINT, SIG_IGN) == SIG_ERR){  
        ImprimirError("Error en la senyal.\n");
    }

    if(signal(SIGQUIT, SIG_IGN) == SIG_ERR){  
        ImprimirError("Error en la senyal.\n");
    }
}

void ImprimirInfoControlador(char *text)
{
    char infoColor[strlen(capInfoControlador) + strlen(text) + MIDA_MAX_CADENA_COLORS * 2];

    sprintf(infoColor, "%s%s%s%s", INVERTIR_COLOR, capInfoControlador, text, FI_COLOR);

    if (write(1, infoColor, strlen(infoColor)) == -1)
        ImprimirError("ERROR write ImprimirInfoControlador");
}

void ImprimirError(char *text)
{

    char infoColorError[strlen(capInfoControlador) + strlen(text) + MIDA_MAX_CADENA_COLORS * 2];

    sprintf(infoColorError, "%s%s%s: %s%s\n", FORMAT_TEXT_ERROR, capInfoControlador, text, strerror(errno), FI_COLOR);
    write(2, "\n", 1);
    write(2, infoColorError, strlen(infoColorError));
    write(2, "\n", 1);

    exit(EXIT_FAILURE);
}