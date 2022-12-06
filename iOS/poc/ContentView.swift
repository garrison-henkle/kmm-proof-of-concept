//
//  ContentView.swift
//  poc
//
//  Created by Garrison Henkle on 12/4/22.
//

import SwiftUI
import shared

struct ContentView: View {
    init(){
        let api = YouVideoApi()
        api.search(query: "world cup", site: nil, resultCount: 50, freshness: Freshness.any){ responseKt, error in
            let response = Response<VideoList>(responseKt!)
            switch(response){
            case .success(let resp):
                let first = resp.response.data.first
                print(first.debugDescription)
            case .error(let error):
                print("Error occurred: \(error.ex)")
            }
        }
    }
    var body: some View {
        VStack {
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundColor(.accentColor)
            Text("Hello, world!")
        }
        .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

public enum Response<T : AnyObject> {

    case error(ResponseError<T>)
    case success(ResponseSuccess<T>)

    public var sealed: shared.Response {
        switch self {
        case .error(let obj):
            return obj as shared.Response
        case .success(let obj):
            return obj as shared.Response
        }
    }

    public init(_ obj: shared.Response) {
        if let obj = obj as? shared.ResponseError<T> {
            self = .error(obj)
        } else if let obj = obj as? shared.ResponseSuccess<T> {
            self = .success(obj)
        } else {
            fatalError("ResponseKs not synchronized with Response class")
        }
    }
}
