namespace java pkg

struct OneStruct {
    1:optional i64 id;
    2:optional string name;
}

service HelloSvc {
   string hello_func()
}