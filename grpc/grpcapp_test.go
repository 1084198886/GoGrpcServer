package grpc

import (
	"math/rand"
	"testing"
	"time"
)

func init() {
	rand.Seed(time.Now().UnixNano())
}

func TestGrpc(t *testing.T) {
	//user := User{usrid: "001",
	//	status:  proto.AgentStatus_Status1,
	//	logPath: "/var/log/proxy.log",
	//}
	//user.StartServer()
}
