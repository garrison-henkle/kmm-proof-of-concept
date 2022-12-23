//
//  ContentView.swift
//  poc
//
//  Created by Garrison Henkle on 12/4/22.
//

import SwiftUI
import YouComponents

struct ContentView: View {
    @StateObject private var videoRepo: VideoRepo
    
    init(){
//        _videoRepo = StateObject(wrappedValue: VideoRepo())
        CachedEndpointCompanion().initializeCacheDirectory(androidFilesDir: nil){ _, _ in }
        let api = YouVideoApi()
        let repo = VideoRepository(api: api, filesDir: nil)
        _videoRepo = StateObject(wrappedValue: VideoRepo(repo: repo))
    }
    
    var body: some View {
        Text(videoRepo.videos.debugDescription)
//        Text(videoRepo.responseString ?? "Hello World")
    }
}

//class VideoRepo: ObservableObject{
//    @Published var responseString: String? = nil
//
//    init(){
//        let url = URL(string: "https://staging.you.com/api/video?q=world%20cup&count=50&page=1")
//        let task = URLSession.shared.dataTask(with: url!) { data, response, error in
//            guard let data = data else { return }
//            self.responseString = String(data: data, encoding: .utf8)
//            let filename = getDocumentDirectory().appendingPathComponent("test.json")
//            let wow: String = self.responseString!
//            do{
//                print(wow)
//                try wow.write(to: filename, atomically: true, encoding: String.Encoding.utf8)
//            } catch{
//                print("error saving json")
//            }
//        }
//        task.resume()
//    }
//}

//func getDocumentDirectory() -> URL{
//    return FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)[0]
//}

class VideoRepo: ObservableObject{
    @Published var videos: VideoList = VideoList(data: [])

    private var closable: Closable? = nil
    init(repo: VideoRepository){
        repo.getVideos(query: "world cup", site: nil, count: 50, page: 1, freshness: Freshness.any, id: 0){ responseKt, error in
            let response = Response<VideoList>(responseKt!)
            switch(response){
            case .success(let resp):
                DispatchQueue.main.async {
                    self.videos = resp.result
                }
            case .error(let error):
                print("Error occurred: \(error.ex)")
            }
        }
    }

    deinit{
        closable?.close()
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

    public var sealed: YouComponents.Response {
        switch self {
        case .error(let obj):
            return obj as YouComponents.Response
        case .success(let obj):
            return obj as YouComponents.Response
        }
    }

    public init(_ obj: YouComponents.Response) {
        if let obj = obj as? YouComponents.ResponseError<T> {
            self = .error(obj)
        } else if let obj = obj as? YouComponents.ResponseSuccess<T> {
            self = .success(obj)
        } else {
            fatalError("ResponseKs not synchronized with Response class")
        }
    }
}
