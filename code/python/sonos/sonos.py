#!/usr/bin/env python3

from soco import SoCo
import argparse


def get_arguments():
    parser = argparse.ArgumentParser()
    parser.add_argument('-i', '--ip', help='Ip-Address from the sonos', required=True)
    parser.add_argument('-c', '--cmd', help='Specifies the Command which will execute', required=True)
    parser.add_argument('-p', '--param', help='Specifies the param for the command if a param is needed')

    args = parser.parse_args()
    return args


def get_volume(sonos_box):
    return sonos_box.volume


def set_volume(sonos_box, volume):
    sonos_box.volume = volume


def get_queue(sonos_box):
    return sonos_box.get_queue()


def get_current_track(sonos_box):
    return device.get_current_track_info()

if __name__ == '__main__':
    device = SoCo(get_arguments().ip)

    cmd = get_arguments().cmd
    output = ""

    if cmd == "get_volume" or cmd == "getVolume":
        output = get_volume(device)

    if cmd == "set_volume" or cmd == "setVolume":
        p = get_arguments().param
        vol1 = get_volume(device)
        set_volume(device, p)
        output = str(vol1) + "-->" + str(get_volume(device))

    if cmd == "volume_louder" or cmd == "volumeLouder" or cmd == "louder":
        p = get_arguments().param
        vol1 = get_volume(device)
        set_volume(device, get_volume(device) + int(p))
        output = str(vol1) + "-->" + str(get_volume(device))

    if cmd == "volume_quieter" or cmd == "volumeQuieter" or cmd == "quieter":
        p = get_arguments().param
        vol1 = get_volume(device)
        set_volume(device, get_volume(device) - int(p))
        output = str(vol1) + "-->" + str(get_volume(device))

    if cmd == "get_queue" or cmd == "getQueue":
        for item in get_queue(device):
            output += item.title + ","
        output = output.rstrip(',')

    if cmd == "get_current_track" or cmd == "getCurrentTrack":
        info = get_current_track()
        p = get_arguments().param.split(',')
        for param in p:
            output += info[param] + ","
        output = output.rstrip(',')

    print(output)
