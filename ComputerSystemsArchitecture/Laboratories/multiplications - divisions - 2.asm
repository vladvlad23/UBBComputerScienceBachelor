bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 10
    b db 10
    c db 10
    d db 0
    
    e dw 0
    f dw 0
    g dw 40
    h dw 0

; our code starts here
segment code use32 class=code
    start:
        mov AL, [a] ; AL = a
        add AL, [b] ; AL = a+b 
        add AL, [c] ; AL = a+b+c
        mov CL, 2 ; CL=2
        mul CL ; AX = (a+b+c)*2 - not sure if i should move back to AL (or if at all possible as the number might be too big)
        mov CX, 3
        mul CX ; DX:AX = [(a+b+c)*2]*3
        mov CX, [g]
        div CX ; AX = DX:AX/CX and DX = DX:AX%CX < = > AX = [(a+b+c)*2]*3/g and DX= [(a+b+c)*2]*3%g
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
