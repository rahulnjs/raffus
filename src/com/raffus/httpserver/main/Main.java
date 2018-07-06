package com.raffus.httpserver.main;

import com.raffus.httpserver.server.Server;
import com.raffus.httpserver.server.ServerOptions;

public class Main {
	private static final String VER = "1.0";

	public static void main(String[] s) throws Exception {
		printLogo();
		ServerOptions opts = buildServerOption(s);
		if (opts.needToPrintHelp()) {
			printHelp();
			System.exit(0);
		}
		if (opts.needToVersion()) {
			System.out.println(VER);
			System.exit(0);
		}
		Server server = new Server(opts);
		if (server != null) {
			server.start();
		}
	}

	private static ServerOptions buildServerOption(String[] args) {
		String EMPTY_STRING="";
		ServerOptions opt = new ServerOptions();
		int i = 0; 
		while(i < args.length) {
			if("pdc".indexOf(args[i].charAt(1)) >= 0) {
				opt.put(args[i],args[++i]);
			} else { 
				opt.put(args[i],EMPTY_STRING);
			}
			i++;
		}
		return opt;
	}

	private static void printLogo() {
		p("\n\n");
		p("* * * * * * *                                                                               * * * * * * *");
		p("*             *                                                                           *");
		p("*              *                      * * * * *         * * * * *                        *");
		p("*               *                   *           *     *           *                      *");
		p("*                *                  *            *    *            *                     *");
		p("*                *                  *            *    *            *                      * ");
		p("*               *                   *                 *                                     *");
		p("*              *                    *                 *                                        *");
		p("*            *                      *                 *                                            *");
		p("*   * * * *           * * * *       *                 *               *               *               * ");
		p("* *                 *         *     *                 *               *               *                  *");
		p("*   *                         *   * * * * * *       * * * * * *       *               *                   *");
		p("*     *                       *     *                 *               *               *                    *");
		p("*       *           * * * * * *     *                 *               *               *                    *");
		p("*         *         *         *     *                 *               *               *                    *");
		p("*           *       *         *     *                 *               *               *                   *");
		p("*             *     *         *     *                 *                *             *                  *");
		p("*              *    *         *     *                 *                  *         *                  *");
		p("*               *     * * * *       *                 *                    * * * *          * * * * *    " + VER);
		p("\n\n");
	}

	private static void printHelp() {
		p("RaffuS help document,");
		p("");
		p("-p        port");
		p("");
		p("          Port to be used to bind the address,");
		p("          if not present default port 4343");
		p("          e.g. raffus -p 8080 ");
		p("");
		p("-d        Document Base");
		p("");
		p("          Defines the root directory of the server");
		p("          if not present then the currect directory");
		p("          will beused as root.");
		p("          e.g. raffus -p <port> -d <root dir>");
		p("");
		p("--v       version");
		p("");
		p("          Server version.");
		p("");
		p("-help     help");
		p("");
		p("          See help document.");
		p("");
		p("-c        caching");
		p("");
		p("          To specify the max age of a cached file,");
		p("          use -1 for no caching.");
		p("          e.g. -c <max-age>");
		p("");
		p("-l        logging");
		p("");
		p("          If present, requests are logged to the");
		p("          console.");
		p("");
		p("");
		p("-verb     verbose");
		p("");
		p("          To log raw requests");
	}

	private static void p(String msg) {
		System.out.println(msg);
	}
	
}