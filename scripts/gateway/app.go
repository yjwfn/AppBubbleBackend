package main

import (
  "flag"
  "net/http"

  "github.com/golang/glog"
  "golang.org/x/net/context"
  "github.com/grpc-ecosystem/grpc-gateway/runtime"
  "google.golang.org/grpc"

  gw "${APP_PROTO_PACKAGE}"
)


var (
  echoEndpoint = flag.String("echo_endpoint", "localhost:8090", "endpoint of ${APP_GATEWAY_SERVICE}")
)

func run() error {
  ctx := context.Background()
  ctx, cancel := context.WithCancel(ctx)
  defer cancel()

  mux := runtime.NewServeMux()
  opts := []grpc.DialOption{grpc.WithInsecure()}
  err := gw.Register${APP_GATEWAY_SERVICE}HandlerFromEndpoint(ctx, mux, *echoEndpoint, opts)
  if err != nil {
    return err
  }

  return http.ListenAndServe(":8080", mux)
}

func main() {
  flag.Parse()
  defer glog.Flush()

  if err := run(); err != nil {
    glog.Fatal(err)
  }
}