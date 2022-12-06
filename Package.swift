// swift-tools-version:5.3
import PackageDescription

let remoteKotlinUrl = "https://api.github.com/repos/garrison-henkle/kmm-proof-of-concept/releases/assets/87248351.zip"
let remoteKotlinChecksum = "a9f87cecd48a657061034471c716421b549d00f8e6fd53f9139531f0168f8aec"
let packageName = "shared"

let package = Package(
    name: packageName,
    platforms: [
        .iOS(.v13)
    ],
    products: [
        .library(
            name: packageName,
            targets: [packageName]
        ),
    ],
    targets: [
        .binaryTarget(
            name: packageName,
            url: remoteKotlinUrl,
            checksum: remoteKotlinChecksum
        )
        ,
    ]
)