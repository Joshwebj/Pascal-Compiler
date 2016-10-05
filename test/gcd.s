# Code file created by Pascal2100 compiler 2015-11-16 13:40:15
        .extern write_char                         
        .extern write_int                         
        .extern write_string                         
        .globl _main                         
        .globl main                         
_main:                                  
main:   call    prog$gcd_1              # Start program
        movl    $0,%eax                 # Set status 0 and
        ret                             # terminate the program
func$gcd_2:                                
        enter   $32,$2                  # Start of gcd
                                        # Start if-statement
        movl    -8(%ebp),%edx           
        movl    12(%edx),%eax           #  var n
        pushl   %eax                    
        movl    $0,%eax                 #  int 0
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        sete    %al                     # Test ==
        cmpl    $0,%eax                 
        je      .L0003                  
        movl    -8(%ebp),%edx           
        movl    8(%edx),%eax            #  var m
        movl    %eax,-32(%ebp)          # GCD :=
        jmp     .L0004                  
.L0003:                                 
        movl    -8(%ebp),%edx           
        movl    8(%edx),%eax            #  var m
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    12(%edx),%eax           #  var n
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    
        movl    %edx,%eax               #  mod
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    12(%edx),%eax           #  var n
        pushl   %eax                    
        call    func$gcd_2              
        addl    $8,%esp                 
        movl    %eax,-32(%ebp)          # GCD :=
.L0004:                                 # End if-statement
        movl    -32(%ebp),%eax          # Get return value
        leave                           # End of gcd
        ret                             
prog$gcd_1:                                
        enter   $36,$1                  # Start of gcd
        movl    $462,%eax               #  int 462
        pushl   %eax                    
        movl    $1071,%eax              #  int 1071
        pushl   %eax                    
        call    func$gcd_2              
        addl    $8,%esp                 
        movl    -4(%ebp),%edx           
        movl    %eax,-36(%edx)          # res :=
        .data                  
.L0005: .asciz   "GCD("
        .align  2              
        .text                  
        leal    .L0005,%eax             # Addr("GCD(")
        pushl   %eax                    # Push param #1.
        call    write_string            
        addl    $4,%esp                 # Pop parameter.
        movl    $1071,%eax              #  int 1071
        pushl   %eax                    # Push param #2.
        call    write_int               
        addl    $4,%esp                 # Pop parameter.
        movl    $44,%eax                #  char 44
        pushl   %eax                    # Push param #3.
        call    write_char              
        addl    $4,%esp                 # Pop parameter.
        movl    $462,%eax               #  int 462
        pushl   %eax                    # Push param #4.
        call    write_int               
        addl    $4,%esp                 # Pop parameter.
        .data                  
.L0006: .asciz   ") = "
        .align  2              
        .text                  
        leal    .L0006,%eax             # Addr(") = ")
        pushl   %eax                    # Push param #5.
        call    write_string            
        addl    $4,%esp                 # Pop parameter.
        movl    -4(%ebp),%edx           
        movl    -36(%edx),%eax          #  var res
        pushl   %eax                    # Push param #6.
        call    write_int               
        addl    $4,%esp                 # Pop parameter.
        movl    $10,%eax                #  char 10
        pushl   %eax                    # Push param #7.
        call    write_char              
        addl    $4,%esp                 # Pop parameter.
        leave                           # End of gcd
        ret                             
