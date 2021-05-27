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
    b db 20
    c db 20
    
    d dw 40

; our code starts here
segment code use32 class=code
    start:
        mov AL,50 ; AL=50
        sub AL, [b] ; AL=50-b
        sub AL, [c] ; AL=50-b-c
        mov BL, 2 ; BL = 2
        mul BL ; AX = (50-b-c)*2
        mov BX,AX ; BX = (50-b-c)*2
        mov AL,[a] ; AL = a
        mov CL,[a] ; BL = a;
        mul CL ; AX = a*a
        add BX,AX ; BX = (50-b-c)*2+a*a
        mov AX, [d]
        add BX,AX ; AX = (50-b-c)*2+a*a+d
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
