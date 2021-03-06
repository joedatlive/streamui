import os
import http.server
import socketserver

class HTTPRequestHandler(http.server.SimpleHTTPRequestHandler):
    def do_GET(self):
        filepath = os.getcwd() + self.path
        cleanpath = os.path.normpath(filepath)
        f = open(cleanpath, 'rb')
        self.send_response(200)
        #self.send_header('Content-type',"text/html")
        self.end_headers()
        self.wfile.write(f.read())
        f.close()

    """Extend SimpleHTTPRequestHandler to handle POST requests"""
    def do_POST(self):
        """Save a file following a HTTP POST request"""
        goodpath = None
        if self.path == "/data/streams":
            goodpath = True
            datapath ="data/config"
            datafile = "streams.json"
        if self.path == "/data/filters":
            goodpath = True
            datapath ="data/config"
            datafile = "filters.json"
        if goodpath == True:
            filename = os.path.join(datapath, datafile)
            file_length = int(self.headers['Content-Length'])
            with open(filename, 'ab') as output_file:
                output_file.write(self.rfile.read(file_length) + "\n".encode('ascii'))
            self.send_response(201, 'Created')
            self.end_headers()
            #reply_body = 'Taved "%s"\n' % filename
            reply_body = '[{"name":"search","value":"Detection"}]'
            self.wfile.write(reply_body.encode('utf-8'))
        else:
            self.send_error(404)
            

if __name__ == '__main__':
    http.server.test(HandlerClass=HTTPRequestHandler, port=80)
