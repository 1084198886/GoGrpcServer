// Code generated by gobind. DO NOT EDIT.

// Java class grpc.Grpc is a proxy for talking to a Go program.
//
//   autogenerated by gobind -lang=java GoGrpc/grpc
package grpc;

import go.Seq;

public abstract class Grpc {
	static {
		Seq.touch(); // for loading the native library
		_init();
	}
	
	private Grpc() {} // uninstantiable
	
	// touch is called from other bound packages to initialize this package
	public static void touch() {}
	
	private static native void _init();
	
	private static final class proxyGrpcDemoService implements Seq.Proxy, GrpcDemoService {
		private final int refnum;
		
		@Override public final int incRefnum() {
		      Seq.incGoRef(refnum, this);
		      return refnum;
		}
		
		proxyGrpcDemoService(int refnum) { this.refnum = refnum; Seq.trackGoRef(refnum, this); }
		
		public native SignInResp signin(String Devphyid) throws Exception;
	}
	
	
	public static native User newUser(String usrid, String logPath, long gRPCPort, String gRPCTLSCert, String gRPCTLSKey, String gRPCCACert, GrpcDemoService service);
}
