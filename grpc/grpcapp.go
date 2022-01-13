package grpc

import (
	"GoGrpc/proto"
	"context"
	"crypto/tls"
	"crypto/x509"
	"errors"
	"fmt"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials"
	"google.golang.org/grpc/keepalive"
	"gopkg.in/natefinch/lumberjack.v2"
	"log"
	"math/rand"
	"net"
	"path"
	"sync"
	"time"
)

//go:generate protoc --go_out=../proto --go_opt=paths=source_relative --go-grpc_out=../proto --go-grpc_opt=paths=source_relative app.proto

func init() {
	rand.Seed(time.Now().UnixNano())
}

type User struct {
	usrid       string
	grpcServer  *grpc.Server
	gRPCPort    int
	gRPCTLSCert string
	gRPCTLSKey  string
	gRPCCACert  string

	status    proto.AgentStatus
	startTime time.Time
	cancel    context.CancelFunc
	logPath   string
	service   GrpcDemoService
}

/*外部业务接口*/
type GrpcDemoService interface {
	Signin(Devphyid string) (*SignInResp, error)
}

type SignInReq struct {
	Devphyid string
}
type SignInResp struct {
	Devphyid string
	Retcode  int
	Retmsg   string
}

func NewUser(usrid string, logPath string, gRPCPort int, gRPCTLSCert, gRPCTLSKey, gRPCCACert string, service GrpcDemoService) *User {
	return &User{
		usrid:       usrid,
		status:      proto.AgentStatus_Status1,
		gRPCPort:    gRPCPort,
		gRPCTLSCert: gRPCTLSCert,
		gRPCTLSKey:  gRPCTLSKey,
		gRPCCACert:  gRPCCACert,
		logPath:     logPath,
		service:     service,
	}
}

func (u *User) Run() error {
	log.Println("grpcgo start server")
	//u.setLogOutput()
	log.Printf("%v grpcgo start server", time.Now())
	ctx, cancel := context.WithCancel(context.Background())
	u.cancel = cancel
	serverError := make(chan error)

	wg := sync.WaitGroup{}
	go func(ctx context.Context) {
		defer wg.Done()
		err := u.background(ctx)
		if err != nil {
			serverError <- err
			cancel()
		}
		log.Printf("%v agentgo background task finished", time.Now())
	}(ctx)

	wg.Add(1)
	go func(ch chan error) {
		defer wg.Done()
		err := u.startGRPCServer()
		if err != nil {
			serverError <- err
			cancel()
		}
		log.Printf("%v gRPC server finished", time.Now())
	}(serverError)
	wg.Wait()
	log.Printf("%v agentgo server quit", time.Now())
	select {
	case e := <-serverError:
		return e
	default:
		return nil
	}
}

func (u *User) startGRPCServer() error {
	addr := fmt.Sprintf(":%v", u.gRPCPort)
	lis, err := net.Listen("tcp", addr)
	if err != nil {
		log.Printf("agentgo grpcserver listen recv remote request err: %v", err)
		return err
	}
	defer lis.Close()
	certPool := x509.NewCertPool()
	if !certPool.AppendCertsFromPEM([]byte(u.gRPCCACert)) {
		log.Printf("agentgo AppendCertsFromPEM err %v", "can not load ca certificate")
		return errors.New("can't load ca certificate")
	}
	//a.config.gRPCTLSCert a.config.gRPCTLSKey
	certificate, err := tls.X509KeyPair([]byte(u.gRPCTLSCert),
		[]byte(u.gRPCTLSKey))
	if err != nil {
		log.Printf("agentgo X509KeyPair can't load server certificate: %v", err)
		return errors.New("can't load server certificate")
	}

	creds := credentials.NewTLS(&tls.Config{
		ClientAuth:   tls.NoClientCert,
		ClientCAs:    certPool,
		Certificates: []tls.Certificate{certificate},
	})
	var opts = []grpc.ServerOption{grpc.Creds(creds),
		grpc.KeepaliveParams(keepalive.ServerParameters{MaxConnectionIdle: 5 * time.Minute, Timeout: 5 * time.Minute}),
		grpc.ConnectionTimeout(time.Duration(5) * time.Minute)}
	u.grpcServer = grpc.NewServer(opts...)
	proto.RegisterAppSignDeviceServer(u.grpcServer, newSignServer(u))
	u.startTime = time.Now()
	log.Printf("agentgo Start grpc server addr: %v ...", addr)
	return u.grpcServer.Serve(lis)
}

type AppSignDeviceServerImpl struct {
	proto.AppSignDeviceServer
	user *User
}

func (app *AppSignDeviceServerImpl) Signin(ctx context.Context, req *proto.AppSign) (*proto.AppSignResponse, error) {
	log.Printf("recv req Devphyid:%v", req.Devphyid)
	_, _ = app.user.service.Signin(req.Devphyid)

	response := &proto.AppSignResponse{
		Retcode:  0,
		Retmsg:   "已收到",
		Devphyid: req.Devphyid,
	}
	return response, nil
}

func newSignServer(user *User) proto.AppSignDeviceServer {
	return &AppSignDeviceServerImpl{user: user}
}

func (u *User) background(ctx context.Context) error {
	ticker := time.NewTicker(time.Second * time.Duration(30))
	defer ticker.Stop()
	for {
		select {
		case <-ctx.Done():
			return nil
		case <-ticker.C:
			log.Printf("%v app background task error:%v", time.Now(), "nio")
		}
	}
}

func (u *User) setLogOutput() {
	log.Println("setLogOutput  start,logPath=", u.logPath)
	if len(u.logPath) == 0 {
		return
	}
	log.Println("setLogOutput flag")
	log.SetOutput(&lumberjack.Logger{
		Filename:   path.Join(u.logPath, "grpcdemo.log"),
		MaxSize:    500, // megabytes
		MaxBackups: 3,
		MaxAge:     28,   //days
		Compress:   true, // disabled by default
	})
}

func (u *User) StopServer() {
	u.cancel()
}
