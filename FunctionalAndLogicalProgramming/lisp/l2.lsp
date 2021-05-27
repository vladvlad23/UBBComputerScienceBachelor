;1. For a given tree of type (1) return the path from the root node to 
;a certain given nodeX

; for A 2 B 2 D 0 E 1 F 2 G 0 H 0 C 2 I 0 J 1 K 0 AND F it should return A B E F
; for A 2 B 2 D 0 E 1 F 2 G 0 H 0 C 2 I 0 J 1 K 0 AND J it should return A C J

(defun proccessLeft (givenList nodes edges)
    (cond
        ((null givenList) nil)
        ((> nodes edges) nil)
        (t (append
            (list (car givenList) (cadr givenList))
            (proccessLeft (cddr givenList) (+ 1 nodes) (+ (cadr givenList) edges))
        ))
    )
)

(defun contains (givenList element)
    (cond
        ((null givenList) nil)
        ((equal (car givenList) element) t)
        (t (contains (cdr givenList) element)) 
    )
)

(defun differences (givenList sublist)
    (cond
        ((null sublist) givenList)
        ((equal (car givenList) (car sublist)) (differences (cdr givenList) (cdr sublist)))
        ; here one should throw error xD
    )

)

(defun reverseList (givenList) 
    (cond 
        ((null givenList) nil)
        ((append (reverseList (cdr givenList)) (list (car givenList))))
    )
)

; if you want the reverse order, i'll have to use an accumulator variable to
; save in a list and then reverse it
(defun proccessNode (givenList searchedValue)
    (setq leftSide (proccessLeft (cddr givenList) 0 0))
    (setq rightSide (differences (cddr givenList) leftSide))
    (cond
        ((null givenList) nil)
        ((equal (car givenList) searchedValue) (write searchedValue))
        (
            (contains leftSide searchedValue) (write (car givenList))
            (proccessNode leftSide searchedValue)
        )
        (
            (contains rightSide searchedValue) (write (car givenList))
            (proccessNode rightSide searchedValue)
        )
    ) 
)
