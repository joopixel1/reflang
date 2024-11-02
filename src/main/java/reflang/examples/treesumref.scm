(define leaf
  (lambda
    (x)
    (lambda
      (id)
      (if 
        (= id 0)
        x
        (list)
      )
    )
  )
)

(define root
  (lambda
    (px py)
    (let
        ( (rx (ref px)) (ry (ref py)) )
        (lambda 
            (id)
            (if
                (= id 0)
                (let 
                    ( (x (deref rx)) (y (deref ry)) )
                    ( + (if (null? x) 0 (x 0)) (if (null? y) 0 (y 0)) )
                )
                (if
                    (= id 1)
                    rx
                    ry
                )
            )
        )
        
    )
  )
)

(define treesum
  (lambda
    (p)
    (p 0)
  )
)

(define getleft
    (lambda 
        (p)
        (p 1)
    )
)

(define getright
    (lambda 
        (p)
        (p 2)
    )
)