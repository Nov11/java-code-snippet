include "hello.thrift"

struct ListStruct{
    1:optional list<hello.Item> items;
}